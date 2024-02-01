package com.example.vmrentalrest.exceptions;


public class ErrorMessages {
    public class BadRequestErrorMessages {
        public static final String USER_ALREADY_EXISTS_MESSAGE = "{USER_ALREADY_EXISTS}";
        public static final String CLIENT_HAS_TOO_MANY_RENTS_MESSAGE = "{CLIENT_HAS_TOO_MANY_RENTS}";
        public static final String DEVICE_ALREADY_RENTED_MESSAGE = "{DEVICE_ALREADY_RENTED}";
        public static final String USERNAME_MUST_BE_BETWEEN_3_AND_30_CHARACTERS_MESSAGE = "{USERNAME_MUST_BE_BETWEEN_3_AND_30_CHARACTERS}";
        public static final String PASSWORD_MUST_BE_BETWEEN_3_AND_64_CHARACTERS_MESSAGE = "{PASSWORD_MUST_BE_BETWEEN_3_AND_64_CHARACTERS}";
        public static final String FIRSTNAME_MUST_BE_BETWEEN_3_AND_30_CHARACTERS_MESSAGE = "{FIRSTNAME_MUST_BE_BETWEEN_3_AND_30_CHARACTERS}";
        public static final String LASTNAME_MUST_BE_BETWEEN_3_AND_30_CHARACTERS_MESSAGE = "{LASTNAME_MUST_BE_BETWEEN_3_AND_30_CHARACTERS}";
        public static final String CITY_MUST_BE_BETWEEN_2_AND_30_CHARACTERS_MESSAGE = "{CITY_MUST_BE_BETWEEN_2_AND_30_CHARACTERS}";
        public static final String STREET_MUST_BE_BETWEEN_2_AND_30_CHARACTERS_MESSAGE = "{STREET_MUST_BE_BETWEEN_2_AND_30_CHARACTERS}";
        public static final String HOUSE_NUMBER_MUST_BE_BETWEEN_1_AND_10_CHARACTERS_MESSAGE = "{HOUSE_NUMBER_MUST_BE_BETWEEN_1_AND_10_CHARACTERS}";
        public static final String CLIENT_TYPE_IS_NULL_MESSAGE = "{CLIENT_TYPE_IS_NULL}";
        public static final String ADDRESS_IS_NULL_MESSAGE = "{ADDRESS_IS_NULL}";
        public static final String USER_IS_NOT_ACTIVE_MESSAGE = "{USER_IS_NOT_ACTIVE)";
        public static final String DATES_ARE_NOT_VALID_MESSAGE = "{DATES_ARE_NOT_VALID}";
        public static final String INVALID_EMAIL_MESSAGE = "{INVALID_EMAIL}";
        public static final String CPU_CORES_MUST_BE_LESS_OR_EQUAL_64_MESSAGE = "{CPU_CORES_MUST_BE_LESS_OR_EQUAL_64}";
        public static final String CPU_CORES_MUST_BE_GREATER_OR_EQUAL_1_MESSAGE = "{CPU_CORES_MUST_BE_GREATER_OR_EQUAL_1}";
        public static final String RAM_MUST_BE_LESS_OR_EQUAL_1024_MESSAGE = "{RAM_MUST_BE_LESS_OR_EQUAL_1024}";
        public static final String RAM_MUST_BE_GREATER_OR_EQUAL_4_MESSAGE = "{RAM_MUST_BE_GREATER_OR_EQUAL_4}";
        public static final String STORAGE_SIZE_MUST_BE_LESS_OR_EQUAL_1024_MESSAGE = "{STORAGE_SIZE_MUST_BE_LESS_OR_EQUAL_1024}";
        public static final String STORAGE_SIZE_MUST_BE_GREATER_OR_EQUAL_32_MESSAGE = "{STORAGE_SIZE_MUST_BE_GREATER_OR_EQUAL_32}";
        public static final String OPERATING_SYSTEM_TYPE_IS_NULL_MESSAGE = "{OPERATING_SYSTEM_TYPE_IS_NULL}";
        public static final String DATABASE_TYPE_IS_NULL_MESSAGE = "{DATABASETYPE_IS_NULL}";
        public static final String PHONE_NUMBER_MUST_HAVE_9_DIGITS_MESSAGE = "{PHONE_NUMBER_MUST_HAVE_9_DIGITS}";
        public static final String CLIENT_ID_IS_NULL_MESSAGE = "{CLIENT_ID_IS_NULL}";
        public static final String VIRTUAL_DEVICE_ID_IS_NULL_MESSAGE = "{VIRTUAL_DEVICE_ID_IS_NULL}";
        public static final String START_DATE_IS_NULL_MESSAGE = "{START_DATE_IS_NULL}";
        public static final String END_DATE_IS_NULL_MESSAGE = "{END_DATE_IS_NULL}";
        public static final String CANT_DELETE_RENT_MESSAGE = "{CANT_DELETE_RENT}";
        public static final String INVALID_DATES_MESSAGE = "{INVALID_DATES}";
        public static final String USER_IS_NOT_A_CLIENT_MESSAGE = "{USER_IS_NOT_A_CLIENT}";
        public static final String BODY_IS_NULL_MESSAGE = "{BODY_IS_NULL}";
        public static final String VIRTUAL_DEVICE_IS_NOT_A_VIRTUAL_PHONE_MESSAGE = "{VIRTUAL_DEVICE_IS_NOT_A_VIRTUAL_PHONE}";
        public static final String VIRTUAL_DEVICE_IS_NOT_A_VIRTUAL_MACHINE_MESSAGE = "{VIRTUAL_DEVICE_IS_NOT_A_VIRTUAL_MACHINE}";
        public static final String VIRTUAL_DEVICE_IS_NOT_A_VIRTUAL_DATABASE_SERVER_MESSAGE = "{VIRTUAL_DEVICE_IS_NOT_A_VIRTUAL_DATABASE_SERVER}";
        public static final String USERID_DOES_NOT_MATCH_RENT_USERID_MESSAGE = "{USERID_DOES_NOT_MATCH_RENT_USERID}";

    }

    public class NotFoundErrorMessages {
        public static final String USER_NOT_FOUND_MESSAGE = "{USER_NOT_FOUND}";
        public static final String VIRTUAL_DEVICE_NOT_FOUND_MESSAGE = "{VIRTUAL_DEVICE_NOT_FOUND}";
        public static final String RENT_NOT_FOUND_MESSAGE = "{RENT_NOT_FOUND}";
    }
    public class InvalidJWSMessages {
        public static final String INVALID_JWS_MESSAGE = "{INVALID_JWS}";
        public static final String NOT_MATCHING_JWS_MESSAGE = "{NOT_MATCHING_JWS}";
    }

}
