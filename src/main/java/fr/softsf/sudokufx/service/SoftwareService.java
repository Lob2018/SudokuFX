package fr.softsf.sudokufx.service;

import fr.softsf.sudokufx.dto.SoftwareDto;
import fr.softsf.sudokufx.interfaces.mapper.ISoftwareMapper;
import fr.softsf.sudokufx.model.Software;
import fr.softsf.sudokufx.repository.SoftwareRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SoftwareService {

    private static final Logger log = LoggerFactory.getLogger(SoftwareService.class);

    private final SoftwareRepository softwareRepository;

    public SoftwareService(SoftwareRepository softwareRepository) {
        this.softwareRepository = softwareRepository;
    }

    public Optional<SoftwareDto> getSoftware() {
        try {
            return softwareRepository.findFirstSoftware()
                    .map(ISoftwareMapper.INSTANCE::mapSoftwareToDto)
                    .or(() -> {
                        log.error("██ No software found.");
                        return Optional.empty();
                    });
        } catch (Exception e) {
            log.error("██ Exception retrieving software : {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<SoftwareDto> updateSoftware(SoftwareDto softwareDto) {
        try {
            Software software = ISoftwareMapper.INSTANCE.mapSoftwareDtoToSoftware(softwareDto);
            Software updatedSoftware = softwareRepository.save(software);
            return Optional.ofNullable(ISoftwareMapper.INSTANCE.mapSoftwareToDto(updatedSoftware));
        } catch (Exception e) {
            log.error("██ Error updating software : {}", e.getMessage(), e);
            return Optional.empty();
        }
    }
}
