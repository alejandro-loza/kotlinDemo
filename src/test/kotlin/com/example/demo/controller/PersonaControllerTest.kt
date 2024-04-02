package com.example.demo.controller

import com.example.demo.repository.Persona
import com.example.demo.repository.PersonaRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable

@SpringBootTest
@AutoConfigureMockMvc
class PersonaControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var personaRepository: PersonaRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setUp() {
        personaRepository.deleteAll()
        personaRepository.save(Persona(nombre = "Alice", edad = 30))
        personaRepository.save(Persona(nombre = "Bob", edad = 25))
    }

    @Test
    fun `getAll should return all personas`() {
        mockMvc.perform(get("/api/personas"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$[0].nombre").value("Alice"))
            .andExpect(jsonPath("$[1].nombre").value("Bob"))
    }

    @Test
    fun `getById should return a persona`() {
        val persona = personaRepository.save(Persona(nombre = "Charlie", edad = 35))
        mockMvc.perform(get("/api/personas/${persona.id}"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.nombre").value("Charlie"))
    }

    @Test
    fun `create should add a new persona`() {
        val persona = Persona(nombre = "David", edad = 40)
        mockMvc.perform(post("/api/personas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(persona)))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.nombre").value("David"))
    }

    @Test
    fun `update should modify an existing persona`() {
        val persona = personaRepository.save(Persona(nombre = "Eve", edad = 45))
        persona.nombre = "Eva"
        mockMvc.perform(put("/api/personas/${persona.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(persona)))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.nombre").value("Eva"))
    }

    @DeleteMapping("/api/personas/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        return if (personaRepository.existsById(id)) {
            personaRepository.deleteById(id)
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

}
