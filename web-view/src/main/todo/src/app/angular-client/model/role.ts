/**
 * ToDo-Web-API
 * ToDo list sample app web frontend API
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
import { Permission } from './permission';
import { SuperUpdateIdPageable } from './superUpdateIdPageable';


export interface Role { 
    /**
     * Role name
     */
    name: string;
    /**
     * Role description
     */
    description?: string;
    /**
     * Role permissions
     */
    permissions: Array<Permission>;
    id?: number;
    version: number;
    /**
     * Last modification date of the task
     */
    updateDate?: string;
    /**
     * Total number of pages
     */
    totalPages?: number;
    /**
     * Current page number
     */
    currentPage?: number;
}

