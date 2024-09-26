package tech.buildrun.springboot.repositories;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import tech.buildrun.springboot.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


/**
 * Interface de repositório para gerenciar entidades ProductModel.
 *
 * <p>Esta interface estende JpaRepository, fornecendo operações CRUD e
 * métodos adicionais relacionados ao JPA para a entidade ProductModel.</p>
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see ProductModel
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {

}
