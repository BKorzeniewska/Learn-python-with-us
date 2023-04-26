package com.example.learnpython.mail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
interface MailTemplateRepository extends JpaRepository<MailTemplate, Long> {

    @Query("SELECT mt FROM MailTemplate mt WHERE mt.type = :type")
    MailTemplate findByType(final MailType type);
}
