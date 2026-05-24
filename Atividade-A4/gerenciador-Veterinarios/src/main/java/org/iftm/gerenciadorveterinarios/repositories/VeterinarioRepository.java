package org.iftm.gerenciadorveterinarios.repositories;

import java.util.List;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.time.Instant;

public interface VeterinarioRepository extends JpaRepository<Veterinario, Integer> {

   @Query("SELECT v FROM Veterinario v WHERE LOWER(v.nome) = LOWER(:nome)")
   public List<Veterinario> findByNomeIgnoreCase(String nome);

   @Query("SELECT v FROM Veterinario v WHERE LOWER(v.nome) LIKE LOWER(CONCAT('%',:nome,'%'))")
   public List<Veterinario> findByNomeContains(String nome);

   public List<Veterinario> findBySalarioGreaterThan(BigDecimal salario);

   public List<Veterinario> findBySalarioLessThan(BigDecimal salario);

   public List<Veterinario> findBySalarioBetween(BigDecimal salarioMinimo, BigDecimal salarioMaximo);

   public List<Veterinario> findByDataNascimentoBetween(Instant dataNascimentoMinima, Instant dataNascimentoMaxima);

   public int countBySalarioGreaterThan(BigDecimal salario);
}
