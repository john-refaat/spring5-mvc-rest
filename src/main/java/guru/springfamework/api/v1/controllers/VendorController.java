package guru.springfamework.api.v1.controllers;

import guru.springfamework.api.v1.mappers.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.services.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author john
 * @since 04/05/2024
 */
@Api(description = "Vendor Endpoint" )
@RestController
@RequestMapping("/api/v1/vendors")
public class VendorController {
    public static final String BASE_PATH = VendorController.class.getAnnotation(RequestMapping.class).value()[0]+"/";

    private final VendorService vendorService;
    private final VendorMapper vendorMapper;


    public VendorController(VendorService vendorService, VendorMapper vendorMapper) {
        this.vendorService = vendorService;
        this.vendorMapper = vendorMapper;
    }

    @ApiOperation(value = "Get The List of All Vendors")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public VendorListDTO getAllVendors() {
        return vendorService.getVendorList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public VendorDTO getVendorById(@PathVariable Integer id) {
        VendorDTO vendorById = vendorService.getVendorById(id);
        vendorById.setSelfLink(BASE_PATH+vendorById.getId());
        return vendorById;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public VendorDTO createVendor(@RequestBody VendorDTO vendorDTO) {
        VendorDTO vendor = vendorService.createVendor(vendorDTO);
        vendor.setSelfLink(BASE_PATH+vendor.getId());
        return vendor;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public VendorDTO updateVendor(@PathVariable Integer id, @RequestBody VendorDTO vendorDTO) {
        VendorDTO vendorDTO1 = vendorService.updateVendor(id, vendorDTO);
        vendorDTO1.setSelfLink(BASE_PATH+id);
        return vendorDTO1;
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public VendorDTO patchVendor(@PathVariable Integer id, @RequestBody VendorDTO vendorDTO) {
        VendorDTO patched = vendorService.patchVendor(id, vendorDTO);
        patched.setSelfLink(BASE_PATH+patched.getId());
        return patched;
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        vendorService.deleteVendorById(id);
    }
}
