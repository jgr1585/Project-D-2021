# OpenApiDefinition.BookingControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**book**](BookingControllerApi.md#book) | **POST** /rest/booking/book | 



## book

> book(bookingDTO)



### Example

```javascript
import OpenApiDefinition from 'open_api_definition';

let apiInstance = new OpenApiDefinition.BookingControllerApi();
let bookingDTO = new OpenApiDefinition.BookingDTO(); // BookingDTO | 
apiInstance.book(bookingDTO, (error, data, response) => {
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
 **bookingDTO** | [**BookingDTO**](BookingDTO.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: Not defined

