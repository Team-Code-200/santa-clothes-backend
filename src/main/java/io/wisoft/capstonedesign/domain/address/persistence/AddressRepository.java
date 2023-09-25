package io.wisoft.capstonedesign.domain.address.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
