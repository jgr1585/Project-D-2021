# OpenApiDefinition.BookingControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**book**](BookingControllerApi.md#book) | **POST** /rest/booking/book | 



## book

> book(categoryIdsAndAmounts, bookingDTO)



### Example

```javascript
import OpenApiDefinition from 'open_api_definition';

let apiInstance = new OpenApiDefinition.BookingControllerApi();
let categoryIdsAndAmounts = {key: null}; // {String: Number} | 
let bookingDTO = new OpenApiDefinition.BookingDTO(); // BookingDTO | 
apiInstance.book(categoryIdsAndAmounts, bookingDTO, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully.');
  }
});
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **categoryIdsAndAmounts** | [**{String: Number}**](Number.md)|  | 
 **bookingDTO** | [**BookingDTO**](.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined

