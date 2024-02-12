package ru.urvanov.virtualpets.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ru.urvanov.virtualpets.server.dao.AchievementDao;
import ru.urvanov.virtualpets.server.dao.JournalEntryDao;
import ru.urvanov.virtualpets.server.dao.LevelDao;
import ru.urvanov.virtualpets.server.dao.PetDao;
import ru.urvanov.virtualpets.server.dao.domain.Achievement;
import ru.urvanov.virtualpets.server.dao.domain.AchievementCode;
import ru.urvanov.virtualpets.server.dao.domain.JournalEntry;
import ru.urvanov.virtualpets.server.dao.domain.JournalEntryType;
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
    private JournalEntryDao journalEntryDao;

    @Autowired
    private AchievementDao achievementDao;
    
    @Autowired
    private PetDao petDao;

    @Autowired
    private ConversionService conversionService;

    @Override
    public GetTownInfoResult getTownInfo() throws DaoException,
            ServiceException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        SelectedPet selectedPet = (SelectedPet) sra.getAttribute("pet",
                ServletRequestAttributes.SCOPE_SESSION);
        Pet pet = petDao.findFullById(selectedPet.getId());

        Map<JournalEntry, PetJournalEntry> mapJournalEntries = pet
                .getJournalEntries();
        JournalEntry playHiddenObjectGames = journalEntryDao
                .findByCode(JournalEntryType.PLAY_HIDDEN_OBJECT_GAMES);
        if (!mapJournalEntries.containsKey(playHiddenObjectGames)) {
            PetJournalEntry petJournalEntry = new PetJournalEntry();
            petJournalEntry.setCreatedAt(new Date());
            petJournalEntry.setPet(pet);
            petJournalEntry.setJournalEntry(playHiddenObjectGames);
            mapJournalEntries.put(playHiddenObjectGames, petJournalEntry);
        }

        Achievement leaveTownAchievement = achievementDao
                .findByCode(AchievementCode.LEAVE_ROOM);
        petService.addAchievementIfNot(pet, leaveTownAchievement);
        
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

        List<AchievementCode> listServerAchievements = petService
                .calculateAchievements(pet);
        ru.urvanov.virtualpets.shared.domain.AchievementCode[] listSharedAchievements = new ru.urvanov.virtualpets.shared.domain.AchievementCode[listServerAchievements
                .size()];
        int n = 0;
        for (AchievementCode ac : listServerAchievements) {
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
