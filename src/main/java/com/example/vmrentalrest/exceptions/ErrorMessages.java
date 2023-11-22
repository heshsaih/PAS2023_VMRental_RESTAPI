package com.example.vmrentalrest.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ErrorMessages {
    public class BadRequestErrorMessages {
        public static final String USER_NOT_ACTIVE_MESSAGE = "{USER_NOT_ACTIVE}";
        public static final String USER_ALREADY_EXISTS_MESSAGE = "{USER_ALREADY_EXISTS}";
        public static final String CLIENT_HAS_TOO_MANY_RENTS_MESSAGE = "{CLIENT_HAS_TOO_MANY_RENTS}";
        public static final String DEVICE_ALREADY_RENTED_MESSAGE = "{DEVICE_ALREADY_RENTED}";
        public static final String USERNAME_IS_TOO_SHORT_MESSAGE = "{USERNAME_IS_TOO_SHORT}";
        public static final String PASSWORD_IS_TOO_SHORT_MESSAGE = "{PASSWORD_IS_TOO_SHORT}";
        public static final String FIRSTNAME_IS_TOO_SHORT_MESSAGE = "{FIRSTNAME_IS_TOO_SHORT}";
        public static final String LASTNAME_IS_TOO_SHORT_MESSAGE = "{LASTNAME_IS_TOO_SHORT}";
        public static final String STREET_IS_TOO_SHORT_MESSAGE = "{STREET_IS_TOO_SHORT}";
        public static final String CITY_IS_TOO_SHORT_MESSAGE = "{CITY_IS_TOO_SHORT}";
        public static final String HOUSE_NUMBER_IS_TOO_SHORT_MESSAGE = "{HOUSE_NUMBER_IS_TOO_SHORT}";
        public static final String STORAGE_SIZE_IS_TOO_SMALL_MESSAGE = "{STORAGE_SIZE_IS_TOO_SMALL}";
        public static final String RAM_SIZE_IS_TOO_SMALL_MESSAGE = "{RAM_SIZE_IS_TOO_SMALL}";
        public static final String CPU_NUMBER_IS_TOO_SMALL_MESSAGE = "{CPU_NUMBER_IS_TOO_SMALL}";
        public static final String USERNAME_IS_TOO_LONG_MESSAGE = "{USERNAME_IS_TOO_LONG}";
        public static final String PASSWORD_IS_TOO_LONG_MESSAGE = "{PASSWORD_IS_TOO_LONG}";
        public static final String FIRSTNAME_IS_TOO_LONG_MESSAGE = "{FIRSTNAME_IS_TOO_LONG}";
        public static final String LASTNAME_IS_TOO_LONG_MESSAGE = "{LASTNAME_IS_TOO_LONG}";
        public static final String STREET_IS_TOO_LONG_MESSAGE = "{STREET_IS_TOO_LONG}";
        public static final String CITY_IS_TOO_LONG_MESSAGE = "{CITY_IS_TOO_LONG}";
        public static final String HOUSE_NUMBER_IS_TOO_LONG_MESSAGE = "{HOUSE_NUMBER_IS_TOO_LONG}";
        public static final String CLIENT_TYPE_IS_NULL_MESSAGE = "{CLIENT_TYPE_IS_NULL}";
        public static final String ADDRESS_IS_NULL_MESSAGE = "{ADDRESS_IS_NULL}";
        public static final String USERNAME_IS_NULL_MESSAGE = "{USERNAME_IS_NULL}";
        public static final String PASSWORD_IS_NULL_MESSAGE = "{PASSWORD_IS_NULL}";
        public static final String FIRSTNAME_IS_NULL_MESSAGE = "{FIRSTNAME_IS_NULL}";
        public static final String LASTNAME_IS_NULL_MESSAGE = "{LASTNAME_IS_NULL}";
        public static final String EMAIL_IS_NULL_MESSAGE = "{EMAIL_IS_NULL}";
        public static final String STREET_IS_NULL_MESSAGE = "{STREET_IS_NULL}";
        public static final String CITY_IS_NULL_MESSAGE = "{CITY_IS_NULL}";
        public static final String HOUSE_NUMBER_IS_NULL_MESSAGE = "{HOUSE_NUMBER_IS_NULL}";
        public static final String USER_TYPE_NOT_SUPPORTED_MESSAGE = "{USER_TYPE_NOT_SUPPORTED}";
        public static final String DATES_ARE_NOT_VALID_MESSAGE = "{DATES_ARE_NOT_VALID}";
        public static final String STORAGE_SIZE_IS_NULL_MESSAGE = "{STORAGE_SIZE_IS_NULL}";
        public static final String RAM_SIZE_IS_NULL_MESSAGE = "{RAM_SIZE_IS_NULL}";
        public static final String CPU_NUMBER_IS_NULL_MESSAGE = "{CPU_NUMBER_IS_NULL}";
        public static final String STORAGE_SIZE_IS_TOO_BIG_MESSAGE = "{STORAGE_SIZE_IS_TOO_BIG}";
        public static final String RAM_SIZE_IS_TOO_BIG_MESSAGE = "{RAM_SIZE_IS_TOO_BIG}";
        public static final String CPU_NUMBER_IS_TOO_BIG_MESSAGE = "{CPU_NUMBER_IS_TOO_BIG}";

        public static final String INVALID_HOUSE_NUMBER_MESSAGE = "{INVALID_HOUSE_NUMBER}";
        public static final String INVALID_STORAGE_SIZE_MESSAGE = "{INVALID_STORAGE_SIZE}";
        public static final String INVALID_RAM_SIZE_MESSAGE = "{INVALID_RAM_SIZE}";
        public static final String INVALID_CPU_NUMBER_MESSAGE = "{INVALID_CPU_NUMBER}";
        public static final String INVALID_EMAIL_MESSAGE = "{INVALID_EMAIL}";
    }

    public class NotFoundErrorMessages {
        public static final String USER_NOT_FOUND_MESSAGE = "{USER_NOT_FOUND}";
        public static final String VIRTUAL_MACHINE_NOT_FOUND_MESSAGE = "{VIRTUAL_MACHINE_NOT_FOUND}";
        public static final String RENT_NOT_FOUND_MESSAGE = "{RENT_NOT_FOUND}";
    }

}
