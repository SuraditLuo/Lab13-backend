package se331.rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import se331.rest.entity.Event;
import se331.rest.entity.Organizer;
import se331.rest.repository.EventRepository;
import se331.rest.repository.OrganizerRepository;
import se331.rest.security.entity.Authority;
import se331.rest.security.entity.AuthorityName;
import se331.rest.security.entity.User;
import se331.rest.security.repository.AuthorityRepository;
import se331.rest.security.repository.UserRepository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class InitApp implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    EventRepository eventRepository;

    @Autowired
    OrganizerRepository organizerRepository;
    @Autowired
    AuthorityRepository authorityRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        Organizer org1, org2, org3;
        org1 = organizerRepository.save(Organizer.builder()
                .name("CAMT").build());
        org2 = organizerRepository.save(Organizer.builder()
                .name("CMU").build());
        org3 = organizerRepository.save(Organizer.builder()
                .name("ChiangMai").build());
        Event tempEvent = null;
        tempEvent = eventRepository.save(Event.builder()
                .category("Academic")
                .title("Midterm Exam")
                .description("A time for taking the exam")
                .location("CAMT Building")
                .date("3rd Sept")
                .time("3.00-4.00 pm.")
                .petAllowed(false)
                .organizer(org1)
                .build());
        org1.getOwnEvents().add(tempEvent);
        tempEvent = eventRepository.save(Event.builder()
                .category("Academic")
                .title("Commencement Day")
                .description("A time for celebration")
                .location("CMU Convention hall")
                .date("21th Jan")
                .time("8.00am-4.00 pm.")
                .petAllowed(false)
                .organizer(org1)
                .build());
        org1.getOwnEvents().add(tempEvent);
        tempEvent = eventRepository.save(Event.builder()
                .category("Cultural")
                .title("Loy Krathong")
                .description("A time for Krathong")
                .location("Ping River")
                .date("21th Nov")
                .time("8.00-10.00 pm.")
                .organizer(org2)
                .petAllowed(false)
                .build());
        org2.getOwnEvents().add(tempEvent);
        tempEvent = eventRepository.save(Event.builder()
                .category("Cultural")
                .title("Songkran")
                .description("Let's Play Water")
                .location("Chiang Mai Moat")
                .date("13th April")
                .time("10.00am - 6.00 pm.")
                .petAllowed(true)
                .organizer(org3)
                .build());
        org3.getOwnEvents().add(tempEvent);
        addUser();
        org1.setUser(user1);
        user1.setOrganizer(org1);
        org2.setUser(user2);
        user2.setOrganizer(org2);
        org3.setUser(user3);
        user3.setOrganizer(org3);
    }
    User user1, user2, user3;
    private  void addUser() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        Authority authUser =
                Authority.builder().name(AuthorityName.ROLE_USER).build();
        Authority authAdmin =
                Authority.builder().name(AuthorityName.ROLE_ADMIN).build();
        user1 = User.builder()
                .username("admin")
                .password(encoder.encode("admin"))
                .firstname("admin")
                .lastname("admin")
                .email("admin@admin.com")
                .enabled(true)
                .lastPasswordResetDate(Date.from(LocalDate.of(2021, 01, 01)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();

        user2 = User.builder()
                .username("user")
                .password(encoder.encode("user"))
                .firstname("user")
                .lastname("user")
                .email("enabled@user.com")
                .enabled(true)
                .lastPasswordResetDate(Date.from(LocalDate.of(2021, 01, 01)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();

        user3 = User.builder()
                .username("disabledUser")
                .password(encoder.encode("disabledUser"))
                .firstname("disabledUser")
                .lastname("disabledUser")
                .email("disabledUser@user.com")
                .enabled(true)
                .lastPasswordResetDate(Date.from(LocalDate.of(2021, 01, 01)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();
        authorityRepository.save(authUser);
        authorityRepository.save(authAdmin);
        user1.getAuthorities().add(authUser);
        user1.getAuthorities().add(authAdmin);
        user2.getAuthorities().add(authUser);
        user3.getAuthorities().add(authUser);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }
}
