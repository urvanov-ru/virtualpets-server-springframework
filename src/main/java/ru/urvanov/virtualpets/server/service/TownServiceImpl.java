package ru.urvanov.virtualpets.server.service;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ru.urvanov.virtualpets.server.dao.LevelDao;
import ru.urvanov.virtualpets.server.dao.PetDao;
import ru.urvanov.virtualpets.server.dao.domain.AchievementId;
import ru.urvanov.virtualpets.server.dao.domain.JournalEntryId;
import ru.urvanov.virtualpets.server.dao.domain.Level;
import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.PetJournalEntry;
import ru.urvanov.virtualpets.server.dao.domain.SelectedPet;
import ru.urvanov.virtualpets.shared.domain.GetTownInfoResult;
import ru.urvanov.virtualpets.shared.domain.LevelInfo;
import ru.urvanov.virtualpets.shared.exception.DaoException;
import ru.urvanov.virtualpets.shared.exception.ServiceException;

@Service
public class TownServiceImpl implements ru.urvanov.virtualpets.shared.service.TownService {

    @Autowired
    private ru.urvanov.virtualpets.server.service.PetService petService;

    @Autowired
    private LevelDao levelDao;
    
    @Autowired
    private PetDao petDao;

    @Autowired
    private ConversionService conversionService;
    
    @Autowired
    private Clock clock;

    @Override
    public GetTownInfoResult getTownInfo() throws DaoException,
            ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findFullById(selectedPet.getId());

        Map<JournalEntryId, PetJournalEntry> mapJournalEntries = pet
                .getJournalEntries();
        if (!mapJournalEntries.containsKey(JournalEntryId.PLAY_HIDDEN_OBJECT_GAMES)) {
            PetJournalEntry petJournalEntry = new PetJournalEntry();
            petJournalEntry.setCreatedAt(OffsetDateTime.now(clock));
            petJournalEntry.setPet(pet);
            petJournalEntry.setJournalEntry(JournalEntryId.PLAY_HIDDEN_OBJECT_GAMES);
            mapJournalEntries.put(JournalEntryId.PLAY_HIDDEN_OBJECT_GAMES, petJournalEntry);
        }

        petService.addAchievementIfNot(pet, AchievementId.LEAVE_ROOM);
        
        petService.addExperience(petDao.findFullById(pet.getId()), 1);
        
        GetTownInfoResult result = new GetTownInfoResult();

        LevelInfo levelInfo = new LevelInfo();
        result.setLevelInfo(levelInfo);
        levelInfo.setLevel(pet.getLevel().getId());
        levelInfo.setExperience(pet.getExperience());
        Level nextLevelLeague = levelDao
                .findById(pet.getLevel().getId() + 1);
        levelInfo.setMaxExperience(nextLevelLeague == null ? Integer.MAX_VALUE
                : nextLevelLeague.getExperience());
        levelInfo.setMinExperience(pet.getLevel().getExperience());

        List<AchievementId> listServerAchievements = petService
                .calculateAchievements(pet);
        ru.urvanov.virtualpets.shared.domain.AchievementCode[] listSharedAchievements = new ru.urvanov.virtualpets.shared.domain.AchievementCode[listServerAchievements
                .size()];
        int n = 0;
        for (AchievementId ac : listServerAchievements) {
            listSharedAchievements[n] = conversionService
                    .convert(
                            ac,
                            ru.urvanov.virtualpets.shared.domain.AchievementCode.class);
            n++;
        }
        result.setAchievements(listSharedAchievements);

        result.setNewJournalEntriesCount(petService
                .getPetNewJournalEntriesCount(pet.getId()));
        petDao.save(pet);
        return result;
    }

}
