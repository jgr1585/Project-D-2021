# OpenApiDefinition.CategoryControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**allCategories**](CategoryControllerApi.md#allCategories) | **GET** /rest/category/all | 
[**availableCategory**](CategoryControllerApi.md#availableCategory) | **GET** /rest/category/available | 



## allCategories

> [CategoryDTO] allCategories()



### Example

```javascript
import OpenApiDefinition from 'open_api_definition';

let apiInstance = new OpenApiDefinition.CategoryControllerApi();
apiInstance.allCategories((error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters

This endpoint does not need any parameter.

### Return type

[**[CategoryDTO]**](CategoryDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


## availableCategory

> [AvailableCategoryDTO] availableCategory(from, until)



### Example

```javascript
import OpenApiDefinition from 'open_api_definition';

let apiInstance = new OpenApiDefinition.CategoryControllerApi();
let from = new Date("2013-10-20"); // Date | 
let until = new Date("2013-10-20"); // Date | 
apiInstance.availableCategory(from, until, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **from** | **Date**|  | 
 **until** | **Date**|  | 

### Return type

[**[AvailableCategoryDTO]**](AvailableCategoryDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

