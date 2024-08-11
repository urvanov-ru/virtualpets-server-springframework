package ru.urvanov.virtualpets.server.service.domain;

/**
 * Запись для передачи первичных ключей текущего пользователя и 
 * выбранного им для игры в данный момент питомца.
 */
public record UserPetDetails(Integer userId, Integer petId) {}