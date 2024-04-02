package com.example.demo.controller

import com.example.demo.repository.Persona
import com.example.demo.repository.PersonaRepository
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/personas")
class PersonaController(
        private val personaRepository: PersonaRepository
) {

    @GetMapping
    fun getAll(): List<Persona> = personaRepository.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): Persona = personaRepository.findById(id).orElseThrow {
        // Specify the exception to throw for clarity
        ChangeSetPersister.NotFoundException()
    }

    @PostMapping
    fun create(@RequestBody persona: Persona): Persona = personaRepository.save(persona)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody persona: Persona): Persona {
        val personaToUpdate = personaRepository.findById(id).orElseThrow {
            // Specify the exception to throw for clarity
            ChangeSetPersister.NotFoundException()
        }
        return personaRepository.save(personaToUpdate.copy(nombre = persona.nombre, edad = persona.edad))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = personaRepository.deleteById(id)
}
