//package ru.urvanov.virtualpets.server.dao.domain;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//
///**
// * Запись из справочника системы достижений.
// */
//@Entity
//@Table(name = "achievement")
//public class Achievement implements Serializable {
//
//    private static final long serialVersionUID = 5719570475809622492L;
//
//    /**
//     * Первичный ключ записи. Новые записи добавляются только скриптами
//     * liquibase, поэтому первичный ключ не генерируется ни в БД, ни в 
//     * Java-коде.
//     */
//    @Id
//    private AchievementCode id;
//
//    /**
//     * Натуральный ключ. Код достижения.
//     */
//    @Enumerated(value = EnumType.STRING)
//    @Column(name = "code")
//    private AchievementCode code;
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public AchievementCode getCode() {
//        return code;
//    }
//
//    public void setCode(AchievementCode code) {
//        this.code = code;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(code);
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//        if (getClass() != obj.getClass())
//            return false;
//        Achievement other = (Achievement) obj;
//        return code == other.code;
//    }
//
//    @Override
//    public String toString() {
//        return "Achievement id=" + id + ", code=" + code;
//    }
//}
