package com.develop.machinecomm.Machinecomm.model;



import com.develop.machinecomm.Machinecomm.model.enums.Perfil;
import com.develop.machinecomm.Machinecomm.model.enums.UserType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)

public class Users implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Column(unique=true)
    private String reg;
    private String rfid;
    private Integer type;

    @Column(nullable = true)
    private String email;
    private String func;
    private String picture;
    private String password;


    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name="PERFIS")
    private Set<Integer> perfis = new HashSet<>();

    public Users(){
        addPerfil(Perfil.USER);
    }

    public Users(Integer id, String reg, String rfid, String name, String func,
                 String email, String password, String picture, UserType userType) {
        super();
        this.id = id;
        this.reg = reg;
        this.rfid = rfid;
        this.name = name;
        this.func = func;
        this.type = (userType==null) ? null : userType.getCod();
        this.email = email;
        this.password = password;
        this.picture = picture;
        addPerfil(Perfil.USER);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Perfil> getPerfis()
    {
        return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
    }

    public void setPerfis(Set<Integer> perfis) {
        this.perfis = perfis;
    }

    public void addPerfil(Perfil perfil) {
        perfis.add(perfil.getCod());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Users)) return false;
        Users user = (Users) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}