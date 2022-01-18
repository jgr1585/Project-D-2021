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

/**
 * The SeasonPriceDTO model module.
 * @module model/SeasonPriceDTO
 * @version v0
 */
class SeasonPriceDTO {
    /**
     * Constructs a new <code>SeasonPriceDTO</code>.
     * @alias module:model/SeasonPriceDTO
     */
    constructor() { 
        
        SeasonPriceDTO.initialize(this);
    }

    /**
     * Initializes the fields of this object.
     * This method is used by the constructors of any subclasses, in order to implement multiple inheritance (mix-ins).
     * Only for internal use.
     */
    static initialize(obj) { 
    }

    /**
     * Constructs a <code>SeasonPriceDTO</code> from a plain JavaScript object, optionally creating a new instance.
     * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
     * @param {Object} data The plain JavaScript object bearing properties of interest.
     * @param {module:model/SeasonPriceDTO} obj Optional instance to populate.
     * @return {module:model/SeasonPriceDTO} The populated <code>SeasonPriceDTO</code> instance.
     */
    static constructFromObject(data, obj) {
        if (data) {
            obj = obj || new SeasonPriceDTO();

            if (data.hasOwnProperty('name')) {
                obj['name'] = ApiClient.convertToType(data['name'], 'String');
            }
            if (data.hasOwnProperty('from')) {
                obj['from'] = ApiClient.convertToType(data['from'], 'String');
            }
            if (data.hasOwnProperty('to')) {
                obj['to'] = ApiClient.convertToType(data['to'], 'String');
            }
            if (data.hasOwnProperty('price')) {
                obj['price'] = ApiClient.convertToType(data['price'], 'Number');
            }
        }
        return obj;
    }


}

/**
 * @member {String} name
 */
SeasonPriceDTO.prototype['name'] = undefined;

/**
 * @member {module:model/SeasonPriceDTO.FromEnum} from
 */
SeasonPriceDTO.prototype['from'] = undefined;

/**
 * @member {module:model/SeasonPriceDTO.ToEnum} to
 */
SeasonPriceDTO.prototype['to'] = undefined;

/**
 * @member {Number} price
 */
SeasonPriceDTO.prototype['price'] = undefined;





/**
 * Allowed values for the <code>from</code> property.
 * @enum {String}
 * @readonly
 */
SeasonPriceDTO['FromEnum'] = {

    /**
     * value: "JANUARY"
     * @const
     */
    "JANUARY": "JANUARY",

    /**
     * value: "FEBRUARY"
     * @const
     */
    "FEBRUARY": "FEBRUARY",

    /**
     * value: "MARCH"
     * @const
     */
    "MARCH": "MARCH",

    /**
     * value: "APRIL"
     * @const
     */
    "APRIL": "APRIL",

    /**
     * value: "MAY"
     * @const
     */
    "MAY": "MAY",

    /**
     * value: "JUNE"
     * @const
     */
    "JUNE": "JUNE",

    /**
     * value: "JULY"
     * @const
     */
    "JULY": "JULY",

    /**
     * value: "AUGUST"
     * @const
     */
    "AUGUST": "AUGUST",

    /**
     * value: "SEPTEMBER"
     * @const
     */
    "SEPTEMBER": "SEPTEMBER",

    /**
     * value: "OCTOBER"
     * @const
     */
    "OCTOBER": "OCTOBER",

    /**
     * value: "NOVEMBER"
     * @const
     */
    "NOVEMBER": "NOVEMBER",

    /**
     * value: "DECEMBER"
     * @const
     */
    "DECEMBER": "DECEMBER"
};


/**
 * Allowed values for the <code>to</code> property.
 * @enum {String}
 * @readonly
 */
SeasonPriceDTO['ToEnum'] = {

    /**
     * value: "JANUARY"
     * @const
     */
    "JANUARY": "JANUARY",

    /**
     * value: "FEBRUARY"
     * @const
     */
    "FEBRUARY": "FEBRUARY",

    /**
     * value: "MARCH"
     * @const
     */
    "MARCH": "MARCH",

    /**
     * value: "APRIL"
     * @const
     */
    "APRIL": "APRIL",

    /**
     * value: "MAY"
     * @const
     */
    "MAY": "MAY",

    /**
     * value: "JUNE"
     * @const
     */
    "JUNE": "JUNE",

    /**
     * value: "JULY"
     * @const
     */
    "JULY": "JULY",

    /**
     * value: "AUGUST"
     * @const
     */
    "AUGUST": "AUGUST",

    /**
     * value: "SEPTEMBER"
     * @const
     */
    "SEPTEMBER": "SEPTEMBER",

    /**
     * value: "OCTOBER"
     * @const
     */
    "OCTOBER": "OCTOBER",

    /**
     * value: "NOVEMBER"
     * @const
     */
    "NOVEMBER": "NOVEMBER",

    /**
     * value: "DECEMBER"
     * @const
     */
    "DECEMBER": "DECEMBER"
};



export default SeasonPriceDTO;

