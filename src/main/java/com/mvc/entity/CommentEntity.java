package com.mvc.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_new")
public class CommentEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "userid")
    UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "newid")
    NewEntity newEntity;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;


    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public NewEntity getNewEntity() {
        return newEntity;
    }

    public void setNewEntity(NewEntity newEntity) {
        this.newEntity = newEntity;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
