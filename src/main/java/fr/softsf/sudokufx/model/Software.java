package fr.softsf.sudokufx.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "software")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Software {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long softwareid;

    @NotNull
    @Setter
    @Size(max = 128)
    private String currentversion;

    @NotNull
    @Setter
    @Size(max = 128)
    private String lastversion;

    @NotNull
    private LocalDateTime createdat;

    @NotNull
    @Setter
    private LocalDateTime updatedat;
}


