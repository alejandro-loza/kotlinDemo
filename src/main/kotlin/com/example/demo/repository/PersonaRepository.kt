package com.example.demo.repository

import org.springframework.data.jpa.repository.JpaRepository

interface PersonaRepository : JpaRepository<Persona, Long>
