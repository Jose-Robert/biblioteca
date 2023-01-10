package br.com.github.biblioteca.shared.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Getter
@Setter
@MappedSuperclass
public class BaseEntity implements Persistable<Long>, Serializable {

    @Column(name = "nome")
    private String nome;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "telefone")
    private String telefone;
    @Column(name = "email")
    private String email;
    @Embedded
    private Endereco endereco;

    @Override
    public Long getId() {
        Long retorno = null;
        try {
            retorno = (Long) getIdField().get(Long.class);
        } catch (Exception e) {
            log.error("erro ao obter getId da entidade ", e);
        }
        return retorno;
    }

    @JsonIgnore
    public Field getIdField() {
        Field retorno = null;
        Class<?> actualClass = getClass();

        try {
            do {
                for (Field fieldSequenceId : actualClass.getDeclaredFields()) {
                    fieldSequenceId.setAccessible(true);
                    if (checkIdField(fieldSequenceId)) {
                        retorno = fieldSequenceId;
                        actualClass = actualClass.getSuperclass();
                    }
                }
            } while (Objects.isNull(retorno) && !Object.class.equals(actualClass));
        } catch (Exception e) {
            log.error("erro ao obter getIdField da entidade ", e);
        }
        return retorno;
    }

    private static boolean checkIdField(Field field) {
        return !Objects.isNull(field.getAnnotation(Id.class));
    }

    public Set<ConstraintViolation<BaseEntity>> validationsConstraintsFails() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(this);
    }

    @Override
    public boolean isNew() {
        return getId() == null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BaseEntity other = (BaseEntity) obj;
        if (getId() == null) {
            if (other.getId() != null)
                return false;
        } else if (!getId().equals(other.getId()))
            return false;
        return true;
    }
}
