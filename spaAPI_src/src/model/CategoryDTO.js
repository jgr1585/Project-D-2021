/**
 * OpenAPI definition
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 *
 */

import ApiClient from '../ApiClient';
import SeasonPriceDTO from './SeasonPriceDTO';

/**
 * The CategoryDTO model module.
 * @module model/CategoryDTO
 * @version v0
 */
class CategoryDTO {
    /**
     * Constructs a new <code>CategoryDTO</code>.
     * @alias module:model/CategoryDTO
     */
    constructor() { 
        
        CategoryDTO.initialize(this);
    }

    /**
     * Initializes the fields of this object.
     * This method is used by the constructors of any subclasses, in order to implement multiple inheritance (mix-ins).
     * Only for internal use.
     */
    static initialize(obj) { 
    }

    /**
     * Constructs a <code>CategoryDTO</code> from a plain JavaScript object, optionally creating a new instance.
     * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
     * @param {Object} data The plain JavaScript object bearing properties of interest.
     * @param {module:model/CategoryDTO} obj Optional instance to populate.
     * @return {module:model/CategoryDTO} The populated <code>CategoryDTO</code> instance.
     */
    static constructFromObject(data, obj) {
        if (data) {
            obj = obj || new CategoryDTO();

            if (data.hasOwnProperty('id')) {
                obj['id'] = ApiClient.convertToType(data['id'], 'String');
            }
            if (data.hasOwnProperty('title')) {
                obj['title'] = ApiClient.convertToType(data['title'], 'String');
            }
            if (data.hasOwnProperty('description')) {
                obj['description'] = ApiClient.convertToType(data['description'], 'String');
            }
            if (data.hasOwnProperty('priceList')) {
                obj['priceList'] = ApiClient.convertToType(data['priceList'], [SeasonPriceDTO]);
            }
        }
        return obj;
    }


}

/**
 * @member {String} id
 */
CategoryDTO.prototype['id'] = undefined;

/**
 * @member {String} title
 */
CategoryDTO.prototype['title'] = undefined;

/**
 * @member {String} description
 */
CategoryDTO.prototype['description'] = undefined;

/**
 * @member {Array.<module:model/SeasonPriceDTO>} priceList
 */
CategoryDTO.prototype['priceList'] = undefined;






export default CategoryDTO;
