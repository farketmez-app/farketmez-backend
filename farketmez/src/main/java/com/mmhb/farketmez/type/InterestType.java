package com.mmhb.farketmez.type;

import lombok.Getter;

@Getter
public enum InterestType {
	CINEMA("Sinema"), RESTAURANT("Restoran ve yemek"), CONCERT("Konserler ve canlı müzik"), THEATRE("Tiyatro"),
	ART_GALLERY("Sanat sergileri ve galeriler"), SPORTS("Spor"), OUTDOOR("Doğa yürüyüşleri ve açık hava"),
	CLUBS("Gece kulüpleri ve eğlence mekanları"), READING("Kitap okuma ve edebiyat"), MUSEUM("Müzeler"),
	KIDS_ACTIVITIES("Çocuk etkinlikleri"), YOGA("Yoga ve spor salonları");

	private String displayName;

	InterestType(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public static InterestType getByName(String name) {
		for (InterestType type : InterestType.values()) {
			if (type.getDisplayName().equalsIgnoreCase(name)) {
				return type;
			}
		}
		return null;
	}
}
