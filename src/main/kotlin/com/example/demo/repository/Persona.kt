package com.example.demo.repository

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id


@Entity
data class Persona(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,
        val nombre: String,
        val edad: Int
)
