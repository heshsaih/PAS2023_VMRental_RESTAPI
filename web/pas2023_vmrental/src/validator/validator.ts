import * as yup from 'yup';
import { CreateUserType, ModifyUserType } from '../types/User';
import { CreateRentType } from '../types/Rent';

const userSchema = yup.object({
    username: yup.string().min(3).max(30),
    firstName: yup.string().min(3).max(30),
    lastName: yup.string().min(3).max(30),
    password: yup.string().min(3).max(30),
    email: yup.string().email().required(),
    address: yup.object({
        city: yup.string().min(2).max(30),
        street: yup.string().min(2).max(30),
        houseNumber: yup.string().min(1).max(10)
    })
});

const modifyUserSchema = yup.object({
    firstName: yup.string().min(3).max(30),
    lastName: yup.string().min(3).max(30),
    password: yup.string().min(3).max(30).nullable(),
    email: yup.string().email().required(),
    address: yup.object({
        city: yup.string().min(2).max(30),
        street: yup.string().min(2).max(30),
        houseNumber: yup.string().min(1).max(10)
    })
});

const createRentSchema = yup.object({
    startLocalDateTime: yup.date(),
    endLocalDateTime: yup.date().min(yup.ref("startLocalDateTime"))
});

export const validateUser = (newUser: CreateUserType) => {
    return userSchema.validate(newUser, {abortEarly: false}).catch(error => error);
};

export const validateModifiedUser = (newUser: ModifyUserType) => {
    return modifyUserSchema.validate(newUser, {abortEarly: false}).catch(error => error);
};

export const validateCreateRent = (newRent: CreateRentType) => {
    return createRentSchema.validate(newRent, {abortEarly: false}).catch(error => error);
};