INSERT INTO client (id, activated, company_name, confirmation_string, email, password_hash, role)
VALUES
  (2, NULL, 'noris network AG', NULL, 'noris@example.com', '$2a$04$ECzh4i81CDyZoGckNpGxPOPBn8zoFicBoypxypG9lP0UK9H3Mz3e.', 'CLIENT'),
  (1, NULL, 'Adidas AG', NULL, 'adidas@example.com', '$2a$04$oZVEnvbNOXyxRwzSih/38u3KOgV6xNdLShHsS.q3PxUMcZdhFa6L2', 'ADMIN'),
  (3, NULL, 'DATEV eG', NULL, 'datev@example.com', '$2a$04$fxGPwEP3ceCIe1vqtY0H3ukxhO2miawLaQnmKm2INRwYwtoajgieS', 'EMPLOYEE');

INSERT INTO employee (id, personnel_number, addition_to_address, bic, birth_date, citizenship, city, country_of_birth,
                      disabled, employer_social_savings_number, family_name, first_name, house_number, iban,
                      maiden_name, marital_status, place_of_birth, sex, social_insurance_number, street, token,
                      zip_code, client_id, entry_date, first_day, place_of_employment, description_of_profession,
                      job_performed, type_of_employment, probation_period, duration_of_probation_period, other_jobs,
                      low_income_employment,
                      level_of_education, professional_training, date_apprenticeship_begins,
                      planed_date_apprenticeship_ends, weekly_working_hours, holiday_entitlement, type_of_contract,
                      working_time_monday, working_time_tuesday, working_time_wednesday, working_time_thursday,
                      working_time_friday, working_time_saturday, working_time_sunday, cost_centre, department_number,
                      employed_in_construction_industry_since, person_group, tax_office_number, identification_number,
                      tax_class, factor, number_of_exemptions_for_children, denomination, statutory_health_insurance,
                      parenthood, health_insurance, pension_insurance, unemployment_insurance, nursing_care_insurance,
                      accident_insurance_risk_tariff, type_of_contract1, contract_fixed_date, contract_conclude_date,
                      description1, description2, amount1, amount2, valid_from1, valid_from2, hourly_wage1,
                      hourly_wage2, valid_from3, valid_from4)
VALUES
  (2, '12345', '', 'BIC2389402', '2010-06-05', 'Deutsch', 'Berlin', 'Deutschland', 'NO', 'ARBEITNEHMERNUMMER23',
   'Mustermann', 'Max', '24', 'DE892347289348', 'Mustermann', 'SINGLE', 'Berlin', 'MALE', '89234978', 'Musterstraße',
   'UUVXGD', '1000', 3, '2011-08-04', '2014-06-05', 'Burger King', 'Burger Brater', 'Brat Burger', 'REGULAR', 'YES',
   'too long',
   'YES', 'YES', 'NOTHING', 'PHD', '2014-06-05', '2014-12-05', 40, 5.4, 'PERMANENTFULL', 6, 6, 6, 6, 6, 5, 5,
   'Gunsenhausen', 'b13', '2014-12-05', 'G101', 1922, 11111111111, 3, 0.123, 50.5, 'RK', 23487623, NULL, '_0', '_0',
   '_0', '_0', 'Coscom', 'PERMANENTFULL', NULL, NULL, '', '', 3000, 4000, NULL, NULL, 20, 20, NULL, NULL),
  (3, '42424', 'Seitengasse', 'THEBIGBIC8989', '1951-07-02', 'Polisch', 'Zabalistadt', 'Dänemark', 'YES',
   'a09sda9sd0u', 'Anders', 'Thomas', '33', 'MX89234729834', 'Eberlein', 'MARRIED', 'Habala', 'FEMALE',
   'VERSNR982034', 'Schwarzstraße', 'WVRNB9', '71818', 1, '2011-08-04', '2014-06-05', 'Burger King', 'Burger Brater',
   'Brat Burger', 'REGULAR', 'YES',
   'way too long',
   'YES', 'YES', 'NOTHING', 'PHD', '2014-06-05', '2014-12-05', 40, 4.5,
   'PERMANENTFULL', 6, 6, 6, 6, 6, 5, 5, 'Gunsenhausen', 'b13', '2014-12-05', 'G101', 4132, 22222222222, 2, 0.95,
   15.0, 'EV', 49857362, NULL, '_0', '_0', '_0', '_0', 'Barmenia', 'PERMANENTFULL', NULL, NULL, '', '', 2000, 6000,
   NULL, NULL, 10, 10, NULL, NULL);