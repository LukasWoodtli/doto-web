package info.woodtli.doto;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("info.woodtli.doto");

        noClasses()
            .that()
                .resideInAnyPackage("info.woodtli.doto.service..")
            .or()
                .resideInAnyPackage("info.woodtli.doto.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..info.woodtli.doto.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
