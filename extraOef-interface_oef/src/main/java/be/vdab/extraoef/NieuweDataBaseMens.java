package be.vdab.extraoef;

import jakarta.validation.constraints.NotBlank;

public record NieuweDataBaseMens(@NotBlank String naam,
                                 @NotBlank String hoogte,
                                 @NotBlank String gewicht) {
}
