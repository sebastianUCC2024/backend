package com.agrigo.ai.entity;

import com.agrigo.common.entity.BaseEntity;
import com.agrigo.farmer.entity.Crop;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recommendations")
@Getter
@Setter
@NoArgsConstructor
public class Recommendation extends BaseEntity {
    
    @ManyToOne
    @JoinColumn(name = "crop_id", nullable = false)
    private Crop crop;
    
    @Column(columnDefinition = "TEXT")
    private String fertilizers;
    
    @Column(columnDefinition = "TEXT")
    private String pesticides;
    
    @Column(columnDefinition = "TEXT")
    private String quantities;
    
    @Column(columnDefinition = "TEXT")
    private String explanation;
    
    @Column(columnDefinition = "TEXT")
    private String rawResponse;
    
    @Enumerated(EnumType.STRING)
    private RecommendationType type;
    
    private Recommendation(Builder builder) {
        this.crop = builder.crop;
        this.fertilizers = builder.fertilizers;
        this.pesticides = builder.pesticides;
        this.quantities = builder.quantities;
        this.explanation = builder.explanation;
        this.rawResponse = builder.rawResponse;
        this.type = builder.type;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private Crop crop;
        private String fertilizers;
        private String pesticides;
        private String quantities;
        private String explanation;
        private String rawResponse;
        private RecommendationType type;
        
        public Builder crop(Crop crop) {
            this.crop = crop;
            return this;
        }
        
        public Builder fertilizers(String fertilizers) {
            this.fertilizers = fertilizers;
            return this;
        }
        
        public Builder pesticides(String pesticides) {
            this.pesticides = pesticides;
            return this;
        }
        
        public Builder quantities(String quantities) {
            this.quantities = quantities;
            return this;
        }
        
        public Builder explanation(String explanation) {
            this.explanation = explanation;
            return this;
        }
        
        public Builder rawResponse(String rawResponse) {
            this.rawResponse = rawResponse;
            return this;
        }
        
        public Builder type(RecommendationType type) {
            this.type = type;
            return this;
        }
        
        public Recommendation build() {
            return new Recommendation(this);
        }
    }
}
