package ru.urvanov.virtualpets.server.service.domain;

/**
 * Запись для передачи первичных ключей текущего пользователя и 
 * выбранного им для игры в данный момент питомца.
 */
public record UserPetDetails(
        /**
         * Первичный ключ пользователя-игрока.
         */
         Integer userId,
         /**
          * Первичный ключ выбранного питомца.
          */
         Integer petId) {}