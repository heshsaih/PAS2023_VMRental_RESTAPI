import { ClientTypeEnum } from "../enums/ClientType.enum";
import { UserTypeEnum } from "../enums/UserType.enum";
import { AddressType } from "./Address";

export interface UserType extends ClientType {
    userType: UserTypeEnum,
    id: string,
    username: string,
    firstName: string,
    active: boolean,
    email: string,
    lastName: string,
    address: AddressType,
    token? : string
};

export interface ClientType {
    clientType?: ClientTypeEnum
};

export interface CreateUserType extends ClientType {
    userType: UserTypeEnum,
    username: string,
    firstName: string,
    lastName: string,
    password: string,
    email: string,
    address: AddressType
};

export interface ModifyUserType {
    id: string,
    firstName: string,
    lastName: string,
    password: string | null,
    email: string,
    address: AddressType,
    clientType: ClientTypeEnum | null
}