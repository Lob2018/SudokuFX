package fr.softsf.sudokufx.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "background")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Background {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long backgroundid;

    @NotNull
    @Setter
    @Size(max = 8)
    private String hexcolor;

    @Size(max = 260)
    @Setter
    private String imagepath;

    @Builder.Default
    @NotNull
    @Setter
    private Boolean isimage = false;
}
