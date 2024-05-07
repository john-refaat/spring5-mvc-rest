package guru.springfamework.services;

import guru.springfamework.api.v1.mappers.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.exceptions.ResourceNotFoundException;
import guru.springfamework.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @author john
 * @since 04/05/2024
 */
@Slf4j
@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public VendorListDTO getVendorList() {
        log.debug("getVendorList() called");
        return new VendorListDTO(vendorRepository.findAll().stream().map(vendorMapper::vendorToVendorDTO).collect(Collectors.toList()));
    }

    @Override
    public VendorDTO getVendorById(Integer id) {
        log.debug("getVendorById({}) called", id);
        return vendorMapper.vendorToVendorDTO(vendorRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public VendorDTO createVendor(VendorDTO vendorDTO) {
        log.debug("createVendor({}) called", vendorDTO);
        return vendorMapper.vendorToVendorDTO(vendorRepository.save(vendorMapper.vendorDTOToVendor(vendorDTO)));
    }

    @Override
    public VendorDTO updateVendor(Integer id, VendorDTO vendorDTO) {
        log.debug("updateVendor({}) called", vendorDTO);
        vendorDTO.setId(id);
        return vendorMapper.vendorToVendorDTO(vendorRepository.save(vendorMapper.vendorDTOToVendor(vendorDTO)));
    }

    @Override
    public VendorDTO patchVendor(Integer id, VendorDTO vendorDTO) {
        log.debug("patchVendor({}) called", vendorDTO);
        Vendor patchVendor = vendorRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        if (vendorDTO!=null && vendorDTO.getName()!=null)
            patchVendor.setName(vendorDTO.getName());
        if (vendorDTO!=null && vendorDTO.getPhone()!=null)
            patchVendor.setPhone(vendorDTO.getPhone());
        if (vendorDTO!=null && vendorDTO.getEmail()!=null)
            patchVendor.setEmail(vendorDTO.getEmail());

        return vendorMapper.vendorToVendorDTO(vendorRepository.save(patchVendor));
    }

    @Override
    public void deleteVendorById(Integer id) {
        log.debug("deleteVendorById({}) called", id);
        vendorRepository.deleteById(id);
    }
}
