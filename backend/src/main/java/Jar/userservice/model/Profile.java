package Jar.userservice.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "profiles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_dob", nullable = false)
    private User user;

    @Column(name = "location")
    private String location;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    // Almacenar como JSON en MySQL usando TEXT
    @Column(name = "payment_methods", columnDefinition = "TEXT")
    private String paymentMethodsJson;

    @Column(name = "specific_data", columnDefinition = "TEXT")
    private String specificDataJson;

    // Métodos auxiliares para trabajar con los datos JSON
    // (puedes usar Jackson o Gson para convertir Map <-> JSON String)

    @Transient
    public Map<String, Object> getPaymentMethods() {
        // Aquí deberías parsear paymentMethodsJson a Map
        // Por simplicidad, retornamos un Map vacío
        return new HashMap<>();
    }

    public void setPaymentMethods(Map<String, Object> paymentMethods) {
        // Aquí deberías convertir Map a JSON String
        // Por simplicidad, dejamos vacío
        this.paymentMethodsJson = "{}";
    }

    @Transient
    public Map<String, Object> getSpecificData() {
        return new HashMap<>();
    }

    public void setSpecificData(Map<String, Object> specificData) {
        this.specificDataJson = "{}";
    }
}