package pl.skowrxn.cookie.admin.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class BannerSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    //TODO banner appearance configuration

}
