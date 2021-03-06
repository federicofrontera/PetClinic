package fede.springframework.petclinic.bootstrap;

import fede.springframework.petclinic.model.*;
import fede.springframework.petclinic.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {
    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialtyService specialtyService;
    private final VisitService visitService;

    @Autowired
    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialtyService specialtyService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialtyService = specialtyService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {

        int count = petTypeService.findAll().size();
        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatPetType = petTypeService.save(cat);

        Specialty radiology = new Specialty();
        radiology.setDescription("Radiology");
        Specialty savedRadiology = specialtyService.save(radiology);
        Specialty surgery = new Specialty();
        surgery.setDescription("Surgery");
        Specialty savedSurgery = specialtyService.save(surgery);
        Specialty dentistry = new Specialty();
        dentistry.setDescription("Dentistry");
        Specialty savedDentistry = specialtyService.save(dentistry);


        Owner owner1 = new Owner();
        owner1.setFirstName("Mister");
        owner1.setLastName("Smithson");
        owner1.setAddress("Fake Street 123");
        owner1.setCity("New York");
        owner1.setTelephone("221-456-6549");

        Pet tiger = new Pet();
        tiger.setPetType(savedCatPetType);
        tiger.setOwner(owner1);
        tiger.setBirthDate(LocalDate.now());
        tiger.setName("Tiger");
        owner1.addPet(tiger);

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("John");
        owner2.setLastName("Smith");
        owner2.setAddress("Hello Street 23123");
        owner2.setCity("Dubai");
        owner2.setTelephone("999-8899");

        Pet wolf = new Pet();
        wolf.setPetType(savedDogPetType);
        wolf.setOwner(owner2);
        wolf.setBirthDate(LocalDate.now());
        wolf.setName("Wolf");
        owner2.addPet(wolf);

        ownerService.save(owner2);

        Visit wolfVisit = new Visit();
        wolfVisit.setPet(wolf);
        wolfVisit.setDate(LocalDate.now());
        wolfVisit.setDescription("Wolf has a headache");

        visitService.save(wolfVisit);

        System.out.println("Loaded owners...");

        Vet vet1 = new Vet();
        vet1.setFirstName("First");
        vet1.setLastName("Vet");
        vet1.getSpecialties().add(savedRadiology);
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Second");
        vet2.setLastName("Veterinary");
        vet2.getSpecialties().add(savedSurgery);
        vetService.save(vet2);

        System.out.println("Loaded vets...");
    }
}
