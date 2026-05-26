package com.manish.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "carts")
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(updatable = false)
	private LocalDateTime createdAt;

	private LocalDateTime  updatedAt;
	
	@OneToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "user_id" , nullable = false , unique = true )
	private User user;
	
	
	@OneToMany(mappedBy = "cart" , cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<CartItem> cartItems = new ArrayList<>();
	
	 public BigDecimal getTotalPrice() {
	        return cartItems.stream()
	                .map(CartItem::getSubtotal)
	                .reduce(BigDecimal.ZERO, BigDecimal::add);
	    }

	 @PrePersist
	    protected void onCreate() {
	        createdAt = LocalDateTime.now();
	        updatedAt = LocalDateTime.now();
	    }

	    @PreUpdate
	    protected void onUpdate() {
	        updatedAt = LocalDateTime.now();
	    }
}
