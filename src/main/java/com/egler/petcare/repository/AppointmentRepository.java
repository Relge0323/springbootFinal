package com.egler.petcare.repository;

import com.egler.petcare.model.Appointment;
import com.egler.petcare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUserOrderByAppointmentDateDesc(User user);
    List<Appointment> findByUserUsernameOrderByAppointmentDateDesc(String username);
}