CREATE TABLE users
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    full_name  VARCHAR(255),
    id_type    VARCHAR(255),
    id_no      VARCHAR(255),
    gender     VARCHAR(255),
    address1   VARCHAR(255),
    address2   VARCHAR(255),
    address3   VARCHAR(255),
    country    VARCHAR(255),
    status     VARCHAR(255) NOT NULL,
    journey_id VARCHAR(255)
);

CREATE TABLE ekyc_countries
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(255) NOT NULL UNIQUE,
    alpha2_code  VARCHAR(255) NOT NULL UNIQUE,
    alpha3_code  VARCHAR(255) NOT NULL UNIQUE,
    numeric_code VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE ekyc_documents
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    category   VARCHAR(255) NOT NULL, -- PASSPORT, NON_PASSPORT
    type       VARCHAR(255) NOT NULL, -- ID, DRIVING_LICENSE, VISA, PASSPORT
    name       VARCHAR(255) NOT NULL,
    country_id INT,
    FOREIGN KEY (country_id) REFERENCES ekyc_countries (id)
);

CREATE TABLE ekyc_thresholds
(
    id      INT PRIMARY KEY AUTO_INCREMENT,
    code    VARCHAR(255) NOT NULL,
    label   VARCHAR(255) NOT NULL,
    -- GENERAL, NON_PASSPORT, PASSPORT, FACE_VERIFICATION, LIVENESS,
    type    VARCHAR(255) NOT NULL,
    d_value DECIMAL,               -- decimal value
    s_value VARCHAR(255)           -- Pass, Fail, Cautious
);

-- users data
INSERT INTO users (email, password, status)
VALUES ('ic-user@gmail.com', '$2a$10$vFgV5.ymn0Ye0q8u44ohHu3LNXMcgcRa/WmNiQkjqlShtMu4Xd9Sm', 'PENDING_EKYC'),
       ('passport-user@gmail.com', '$2a$10$vFgV5.ymn0Ye0q8u44ohHu3LNXMcgcRa/WmNiQkjqlShtMu4Xd9Sm', 'PENDING_EKYC');

-- ekyc_countries data
-- https://www.iban.com/country-codes
INSERT INTO ekyc_countries (name, alpha2_code, alpha3_code, numeric_code)
VALUES ('BRUNEI DARUSSALAM', 'BN', 'BRN', '096'),
       ('CAMBODIA', 'KH', 'KHM', '116'),
       ('CHINA', 'CN', 'CHN', '156'),
       ('HONG KONG', 'HK', 'HKG', '344'),
       ('INDONESIA', 'ID', 'IDN', '360'),
       ('MALAYSIA', 'MY', 'MYS', '458'),
       ('MYANMAR', 'MM', 'MMR', '104'),
       ('PHILIPPINES', 'PH', 'PHL', '608'),
       ('SINGAPORE', 'SG', 'SGP', '702'),
       ('THAILAND', 'TH', 'THA', '764'),
       ('UNITED ARAB EMIRATES', 'AE', 'ARE', '784'),
       ('VIET NAM', 'VN', 'VNM', '704');

-- ekyc_documents data
-- https://api2-ekycportal.innov8tif.com/emas-ekyc-portal/centralized-okayid/supported-document-type
INSERT INTO ekyc_documents (category, type, name, country_id)
VALUES
    -- Brunei Darussalam
    ('NON_PASSPORT', 'ID', 'Brunei Darussalam - Id Card # 2', 1),
    ('NON_PASSPORT', 'ID', 'Brunei Darussalam - Id Card #2', 1),
    ('NON_PASSPORT', 'DRIVING_LICENSE', 'Brunei Darussalam - Driving License', 1),
    ('PASSPORT', 'PASSPORT', 'Brunei Darussalam - ePassport (2008)', 1),
    -- Cambodia
    ('NON_PASSPORT', 'ID', 'Cambodia - Id Card #3', 2),
    ('NON_PASSPORT', 'ID', 'Cambodia - Id Card #2', 2),
    ('NON_PASSPORT', 'VISA', 'Cambodia - Visa #4', 2),
    ('PASSPORT', 'PASSPORT', 'Cambodia - ePassport #3', 2),
    -- China
    ('NON_PASSPORT', 'ID', 'China - Id Card #1', 3),
    ('NON_PASSPORT', 'ID', 'China -Id Card (2004)', 3),
    ('NON_PASSPORT', 'ID', 'China - Korean Ethnic Id Card (2004)', 3),
    ('NON_PASSPORT', 'ID', 'China - Zhuang Ethnic Id Card (2004)', 3),
    ('PASSPORT', 'PASSPORT', 'China - Passport (2007) #1', 3),
    -- Hong Kong
    ('NON_PASSPORT', 'ID', 'Hong Kong - Permanent Id Card (2004)', 4),
    ('NON_PASSPORT', 'ID', 'Hong Kong - Permanent Id Card (2018)', 4),
    ('PASSPORT', 'PASSPORT', 'Hong Kong - Passport (2007)', 4),
    -- Indonesia
    ('NON_PASSPORT', 'ID', 'Indonesia - Id Card #2', 5),
    ('PASSPORT', 'PASSPORT', 'Indonesia - Passport #9', 5),
    -- Malaysia
    ('NON_PASSPORT', 'ID', 'Malaysia - Id Card #2', 6),
    ('NON_PASSPORT', 'ID', 'Malaysia - Id Card #1', 6),
    ('NON_PASSPORT', 'DRIVING_LICENSE', 'Malaysia - Driving License #4', 6),
    ('NON_PASSPORT', 'ID', 'Malaysia - Permanent Resident Card #1', 6),
    ('NON_PASSPORT', 'ID', 'Malaysia - Id Card #6', 6),
    ('NON_PASSPORT', 'ID', 'Malaysia - Armed Forces Id Card #1', 6),
    ('NON_PASSPORT', 'ID', 'Malaysia - Police Card #1', 6),
    ('NON_PASSPORT', 'ID', 'Malaysia - Child Id Card #1', 6),
    ('NON_PASSPORT', 'VISA', 'Malaysia - Visa #10', 6),
    ('NON_PASSPORT', 'ID', 'Malaysia - Disabled Id Card #1', 6),
    ('PASSPORT', 'PASSPORT', 'Malaysia - ePassport(2017)', 6),
    -- Myanmar
    ('NON_PASSPORT', 'DRIVING_LICENSE', 'Myanmar - Driving License #1', 7),
    ('PASSPORT', 'PASSPORT', 'Myanmar - Passport #2', 7),
    -- Philippines
    ('NON_PASSPORT', 'ID', 'Philippines - Professional Id Card #1', 8),
    ('NON_PASSPORT', 'DRIVING_LICENSE', 'Philippines - Driving License #1', 8),
    ('NON_PASSPORT', 'DRIVING_LICENSE', 'Philippines - Driving License #2', 8),
    ('NON_PASSPORT', 'DRIVING_LICENSE', 'Philippines - Driving License (2017) #1', 8),
    ('NON_PASSPORT', 'DRIVING_LICENSE', 'Philippines - Driving License (2017) #2 ', 8),
    ('NON_PASSPORT', 'DRIVING_LICENSE', 'Philippines - Driving License (2022)', 8),
    ('NON_PASSPORT', 'ID', 'Philippines - Social Security Card #1', 8),
    ('NON_PASSPORT', 'ID', 'Philippines - UMID Card #1', 8),
    ('NON_PASSPORT', 'ID', 'Philippines - UMID Card #2', 8),
    ('NON_PASSPORT', 'ID', 'Philippines - Voting Card #1', 8),
    ('NON_PASSPORT', 'ID', 'Philippines - Voting Card #2', 8),
    ('NON_PASSPORT', 'ID', 'Philippines - Id Card (2020)', 8),
    ('NON_PASSPORT', 'ID', 'Philippines - Postal Id Card #1', 8),
    ('NON_PASSPORT', 'ID', 'Philippines - Postal Id Card #2', 8),
    ('PASSPORT', 'PASSPORT', 'Philippines - ePassport (2016)', 8),
    ('PASSPORT', 'PASSPORT', 'Philippines - ePassport (2016) Diplomatic', 8),
    ('PASSPORT', 'PASSPORT', 'Philippines - ePassport (2016) Service', 8),
    ('PASSPORT', 'PASSPORT', 'Philippines - ePassport #4', 8),
    ('PASSPORT', 'PASSPORT', 'Philippines - ePassport #5', 8),
    ('PASSPORT', 'PASSPORT', 'Philippines - ePassport #4 Diplomatic', 8),
    ('PASSPORT', 'PASSPORT', 'Philippines - ePassport #5 Diplomatic', 8),
    ('PASSPORT', 'PASSPORT', 'Philippines - ePassport #4 Service', 8),
    ('PASSPORT', 'PASSPORT', 'Philippines - ePassport #5 Service', 8),
    ('PASSPORT', 'PASSPORT', 'Philippines - Passport #1', 8),
    ('PASSPORT', 'PASSPORT', 'Philippines - Passport #2', 8),
    ('PASSPORT', 'PASSPORT', 'Philippines - Passport #3', 8),
    -- Singapore
    ('NON_PASSPORT', 'ID', 'Singapore - Id Card #1', 9),
    ('NON_PASSPORT', 'ID', 'Singapore - Id Card #2', 9),
    ('NON_PASSPORT', 'ID', 'Singapore - Id Card #3', 9),
    ('NON_PASSPORT', 'ID', 'Singapore - Id Card #4', 9),
    ('NON_PASSPORT', 'ID', 'Singapore - Id Card #5', 9),
    ('NON_PASSPORT', 'ID', 'Singapore - Id Card #6', 9),
    ('NON_PASSPORT', 'ID', 'Singapore - Id Card #7', 9),
    ('NON_PASSPORT', 'ID', 'Singapore - Employment Pass #1', 9),
    ('NON_PASSPORT', 'ID', 'Singapore - S Pass #1', 9),
    ('NON_PASSPORT', 'ID', 'Singapore - Work Permit #1', 9),
    ('NON_PASSPORT', 'DRIVING_LICENSE', 'Singapore - Driving License #1', 9),
    ('PASSPORT', 'PASSPORT', 'Singapore - ePassport(2006)', 9),
    ('PASSPORT', 'PASSPORT', 'Singapore - ePassport(2017)', 9),
    -- Thailand
    ('NON_PASSPORT', 'ID', 'Thailand - Id Card #1', 10),
    ('NON_PASSPORT', 'ID', 'Thailand - Id Card #2', 10),
    ('PASSPORT', 'PASSPORT', 'Thailand - ePassport #5', 10),
    -- United Arab Emirates
    ('NON_PASSPORT', 'ID', 'United Arab Emirates - Id Card #2', 11),
    ('NON_PASSPORT', 'ID', 'United Arab Emirates - Driving License Sharjah(1990)', 11),
    ('PASSPORT', 'PASSPORT', 'United Arab Emirates - ePassport(2011)', 11),
    -- Viet Nam
    ('NON_PASSPORT', 'ID', 'Vietnam - Id Card #4', 12),
    ('NON_PASSPORT', 'ID', 'Vietnam - Id Card #1', 12),
    ('PASSPORT', 'PASSPORT', 'Vietnam - Passport(2005)', 12);

INSERT INTO ekyc_thresholds (code, label, type, d_value, s_value)
VALUES
    -- GENERAL
    ('idBlurDetection', 'ID Blur Detection', 'GENERAL', null, 'Pass'),
    ('idBrightnessDetc', 'ID Brightness Detection', 'GENERAL', null, 'Pass'),
    ('faceBrightnessDetc', 'Face Brightness Detection', 'GENERAL', null, 'Pass'),
    ('blurrinessDetection', 'ID Blur Detection', 'GENERAL', null, 'Pass'),
    ('brightnessDetection', 'ID Brightness Detection', 'GENERAL', null, 'Pass'),
    ('screen', 'Screen Detection', 'GENERAL', null, 'Pass'),
    ('idTamper', 'ID Tamper Detection', 'GENERAL', null, 'Pass'),
    ('holocolor', 'Ghost Photo Color Detection', 'GENERAL', null, 'Pass'),
    ('colorDetection', 'Color Mode', 'GENERAL', null, 'Pass'),
    ('colorMode', 'Color Mode', 'GENERAL', null, 'Pass'),
    ('hologram', 'Hologram', 'GENERAL', null, 'Pass'),
    ('substitution', 'Ghost Photo Comparison', 'GENERAL', null, 'Pass'),
    ('contentSubstitution', 'Content Substitution', 'GENERAL', null, 'Pass'),
    ('f-id-no', 'ID No', 'GENERAL', null, 'Pass'),
    ('illuminationLevel', 'Illumination Level', 'GENERAL', null, 'Pass'),
    ('headerColorDiff', 'Header Color Difference', 'GENERAL', null, 'Pass'),

    -- NON_PASSPORT
    -- Brunei
    -- ID Domestic: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/brunei/bn-domestic-id-card/versions
    -- ID Permanent: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/brunei/bn-permanent-id-card/versions
    ('l-bn-did-header', 'Kad Pengenalan Header', 'NON_PASSPORT', 30, null),
    ('l-bn-did-country', 'Negara Brunei Darussalam', 'NON_PASSPORT', 30, null),
    ('l-bn-did-logo', 'Logo', 'NON_PASSPORT', 30, null),
    ('l-bn-did-name', 'Nama', 'NON_PASSPORT', 30, null),
    ('l-bn-did-dob', 'Tarikh Lahir', 'NON_PASSPORT', 30, null),
    ('l-bn-did-gender', 'Jantina', 'NON_PASSPORT', 30, null),
    ('l-bn-did-birth-place', 'Negeri Tempat Lahir', 'NON_PASSPORT', 30, null),
    ('l-bn-did-resident', 'Warganegara', 'NON_PASSPORT', 30, null),
    ('l-bn-pic-id-card', 'Kad Pengenalan Header', 'NON_PASSPORT', 30, null),
    ('l-bn-pic-country', 'Negara Brunei Darussalam', 'NON_PASSPORT', 30, null),
    ('l-bn-pic-logo', 'Logo', 'NON_PASSPORT', 30, null),
    ('l-bn-pic-name', 'Nama', 'NON_PASSPORT', 30, null),
    ('l-bn-pic-dob', 'Tarikh Lahir', 'NON_PASSPORT', 30, null),
    ('l-bn-pic-gender', 'Jantina', 'NON_PASSPORT', 30, null),
    ('l-bn-pic-birth-place', 'Negeri Tempat Lahir', 'NON_PASSPORT', 30, null),
    ('l-bn-pic-permanent-r', 'Penduduk Tetap', 'NON_PASSPORT', 30, null),

    -- Cambodia
    -- ID Front: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/cambodia/kh-id-card-front/versions
    -- ID Back: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/cambodia/kh-id-card-back/versions
    ('l-kh-id-front-landmark1', 'Landmark 1', 'NON_PASSPORT', 30, null),
    ('l-kh-id-front-landmark1', 'Landmark 2', 'NON_PASSPORT', 30, null),
    ('l-kh-id-front-landmark3', 'Landmark 3', 'NON_PASSPORT', 30, null),
    ('l-kh-id-front-landmark4', 'Landmark 4', 'NON_PASSPORT', 30, null),
    ('l-kh-id-front-landmark5', 'Landmark 5', 'NON_PASSPORT', 30, null),
    ('l-kh-id-front-landmark6', 'Landmark 6', 'NON_PASSPORT', 30, null),
    ('l-kh-id-front-landmark7', 'Landmark 7', 'NON_PASSPORT', 30, null),
    ('l-kh-id-back-logo1', 'Logo 1', 'NON_PASSPORT', 30, null),
    ('l-kh-id-back-logo2', 'Logo 2', 'NON_PASSPORT', 30, null),
    ('l-kh-id-back-logo3', 'Logo 3', 'NON_PASSPORT', 30, null),
    ('l-kh-id-back-kingdom', 'Kingdom of Cambodia', 'NON_PASSPORT', 30, null),
    ('l-kh-id-back-national', 'National Religion King', 'NON_PASSPORT', 30, null),
    ('l-kh-id-back-card', 'Khmer Identity Card', 'NON_PASSPORT', 30, null),
    ('l-kh-id-back-2-lines', 'Secruity Statements', 'NON_PASSPORT', 30, null),

    -- China
    -- ID Front: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/china/cn-id-card-front/versions
    -- ID Back: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/china/cn-id-card-back/versions
    ('l-ch-id-name', 'Name Landmark', 'NON_PASSPORT', 30, null),
    ('l-ch-id-sex', 'Sex Landmark', 'NON_PASSPORT', 30, null),
    ('l-ch-id-religion', 'Religion Landmark', 'NON_PASSPORT', 30, null),
    ('l-ch-id-dob', 'DoB Landmark', 'NON_PASSPORT', 30, null),
    ('l-ch-id-address', 'Address Landmark', 'NON_PASSPORT', 30, null),
    ('l-ch-id-residential-id-no', 'Residential ID No Landmark', 'NON_PASSPORT', 30, null),
    ('l-ch-id-back-logo', 'Logo Landmark', 'NON_PASSPORT', 30, null),
    ('l-ch-id-back-great-wall', 'Great Wall Landmark', 'NON_PASSPORT', 30, null),
    ('l-ch-id-back-header', 'Header Landmark', 'NON_PASSPORT', 30, null),

    -- Taiwan
    -- ID Front: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/taiwan/tw-id-card-front/versions
    -- ID Back: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/taiwan/tw-id-card-back/versions
    ('l-tw-id-flag', 'Flag Landmark', 'NON_PASSPORT', 30, null),
    ('l-tw-id-logo', 'Logo Landmark', 'NON_PASSPORT', 30, null),
    ('l-tw-id-stamp', 'Stamp Landmark', 'NON_PASSPORT', 30, null),
    ('l-tw-id-back-barcode', 'Barcode Landmark', 'NON_PASSPORT', 30, null),
    ('l-tw-id-back-logo-1', 'Logo 1 Landmark', 'NON_PASSPORT', 30, null),
    ('l-tw-id-back-logo-2', 'Logo 2 Landmark', 'NON_PASSPORT', 30, null),

    -- Hong Kong
    -- ID Front: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/hong-kong/hk-id-card-front/versions
    -- ID Back: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/hong-kong/hk-id-card-back/versions
    ('l-hk-id-header', 'Header', 'NON_PASSPORT', 30, null),
    ('l-hk-id-dob', 'Date of Birth', 'NON_PASSPORT', 30, null),
    ('l-hk-id-issue-d', 'Date of Issue', 'NON_PASSPORT', 30, null),
    ('l-hk-id-back-logo', 'Logo', 'NON_PASSPORT', 30, null),
    ('l-hk-id-back-chip', 'Chip', 'NON_PASSPORT', 30, null),
    ('l-hk-id-back-logo-petal', 'Petal', 'NON_PASSPORT', 30, null),

    -- Indonesia
    -- ID Front: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/indonesia/idn-e-ktp-front/versions
    -- ID Back: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/indonesia/idn-e-ktp-back/versions
    -- DL Front: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/indonesia/idn-driving-license-2019-front/versions
    -- DL Back: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/indonesia/idn-driving-license-2019-back/versions
    ('l-ektp-front-nik', 'Nik', 'NON_PASSPORT', 30, null),
    ('l-ektp-front-nama-alamat', 'Nama Alamat', 'NON_PASSPORT', 30, null),
    ('l-ektp-front-kecamatan', 'Rt Rw Kecamatan', 'NON_PASSPORT', 30, null),
    ('l-ektp-front-agama', 'Agama', 'NON_PASSPORT', 30, null),
    ('l-ektp-front-penduduk-kartu', 'Penduduk Kartu', 'NON_PASSPORT', 30, null),
    ('l-ektp-back-eagle', 'Eagle Logo', 'NON_PASSPORT', 30, null),
    ('l-ektp-back-header', 'Header', 'NON_PASSPORT', 30, null),
    ('l-ektp-back-map', 'Map', 'NON_PASSPORT', 30, null),
    ('l-id-dl-2019-logo2', 'Logo 2 Landmark', 'NON_PASSPORT', 30, null),
    ('l-id-dl-2019-map', 'Map Landmark', 'NON_PASSPORT', 30, null),
    ('l-id-dl-2019-banner', 'Banner Landmark', 'NON_PASSPORT', 30, null),
    ('l-id-dl-back-flag', 'Flag Landmark', 'NON_PASSPORT', 30, null),
    ('l-id-dl-back-logo', 'Logo Landmark', 'NON_PASSPORT', 30, null),
    ('l-id-dl-back-pattern-left', 'Pattern Left Landmark', 'NON_PASSPORT', 30, null),
    ('l-id-dl-back-pattern-right', 'Pattern Right Landmark', 'NON_PASSPORT', 30, null),

    -- Malaysia
    -- MyKad Front: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/malaysia/mys-mykad-front/versions
    -- MyKad Back: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/malaysia/mys-mykad-back/versions
    -- MyKas Front: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/malaysia/mys-mykas/versions
    -- MyKas Back: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/malaysia/mys-mykas-back/versions
    -- MyPR Front: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/malaysia/mys-mypr/versions
    -- MyPR Back: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/malaysia/mys-mypr-back/versions
    -- MyTentera Front: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/malaysia/mys-mytentera/versions
    -- MyTentera Back: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/malaysia/mys-mytentera-back/versions
    -- MyOKU Front: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/malaysia/mys-myoku/versions
    -- MyOKU Back: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/malaysia/mys-myoku-back/versions
    -- MyKid Front: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/malaysia/mys-mykid/versions
    -- MyKid Back: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/malaysia/mys-mykid-back/versions
    -- MyPOCA: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/malaysia/mys-mypoca/versions
    -- DL: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/malaysia/mys-driving-license/versions
    ('l-mykad-header', 'Kad Pengenalan Header', 'NON_PASSPORT', 30, null),
    ('l-mykad-logo', 'MyKad Logo', 'NON_PASSPORT', 30, null),
    ('l-my-flag-logo', 'Malaysia Flag', 'NON_PASSPORT', 30, null),
    ('l-chip', 'Chip', 'NON_PASSPORT', 30, null),
    ('l-hibiscus', 'Hibiscus', 'NON_PASSPORT', 30, null),
    ('l-msc', 'MSC', 'NON_PASSPORT', 30, null),
    ('microprint', 'Microprint', 'NON_PASSPORT', 41, null),
    ('l-signature', 'Signature Landmark', 'NON_PASSPORT', 30, null),
    ('l-tng', 'TnG Landmark', 'NON_PASSPORT', 30, null),
    ('l-80k', '80k Landmark', 'NON_PASSPORT', 30, null),
    ('l-tower', 'Towers Landmark', 'NON_PASSPORT', 30, null),
    ('l-crown', 'Crown Landmark', 'NON_PASSPORT', 30, null),
    ('l-malaysia', 'Malaysia Text Landmark', 'NON_PASSPORT', 30, null),
    ('l-kppn', 'KPPN Landmark', 'NON_PASSPORT', 30, null),
    ('l-atm', 'ATM Landmark', 'NON_PASSPORT', 0, null),
    ('l-meps', 'MEPS Landmark', 'NON_PASSPORT', 0, null),
    ('l-kas-flag', 'Flag', 'NON_PASSPORT', 30, null),
    ('l-kas-header', 'Header', 'NON_PASSPORT', 30, null),
    ('l-kas-mykas', 'My Kas', 'NON_PASSPORT', 30, null),
    ('l-kas-chip', 'Chip', 'NON_PASSPORT', 30, null),
    ('l-kas-green-block', 'Green Block', 'NON_PASSPORT', 30, null),
    ('l-kas-back-jata', 'National Coat of Arms', 'NON_PASSPORT', 30, null),
    ('l-kas-back-mykas', 'MyKas Logo', 'NON_PASSPORT', 30, null),
    ('l-kas-back-tng', 'Tng Logo', 'NON_PASSPORT', 30, null),
    ('l-kas-back-atm', 'ATM Logo', 'NON_PASSPORT', 30, null),
    ('l-kas-back-chip', 'Chip Logo', 'NON_PASSPORT', 30, null),
    ('l-pr-flag', 'Flag', 'NON_PASSPORT', 30, null),
    ('l-pr-header', 'Header', 'NON_PASSPORT', 30, null),
    ('l-pr-mypr', 'My Pr', 'NON_PASSPORT', 30, null),
    ('l-pr-chip', 'Chip', 'NON_PASSPORT', 30, null),
    ('l-pr-white-block', 'White Block', 'NON_PASSPORT', 30, null),
    ('l-pr-back-chip', 'Chip Logo', 'NON_PASSPORT', 30, null),
    ('l-pr-back-emblem', 'National Coat of Arms', 'NON_PASSPORT', 30, null),
    ('l-pr-back-mypr', 'MyPR Logo', 'NON_PASSPORT', 30, null),
    ('l-tentera-header', 'Header', 'NON_PASSPORT', 30, null),
    ('l-tentera-mytentera', 'My Tentera', 'NON_PASSPORT', 30, null),
    ('l-tentera-flag', 'Flag', 'NON_PASSPORT', 30, null),
    ('l-tentera-chip', 'Chip', 'NON_PASSPORT', 30, null),
    ('l-tentera-msc', 'MSC', 'NON_PASSPORT', 30, null),
    ('l-tentera-hibiscus', 'Hibiscus', 'NON_PASSPORT', 30, null),
    ('l-tentera-back-chip', 'Chip Logo', 'NON_PASSPORT', 30, null),
    ('l-tentera-back-emblem', 'National Coat Of Arms', 'NON_PASSPORT', 30, null),
    ('l-tentera-back-mytentera', 'MyTentera Logo', 'NON_PASSPORT', 30, null),
    ('l-tentera-back-crown', 'Star On The Crown', 'NON_PASSPORT', 30, null),
    ('l-my-oku-logo1', 'Logo 1', 'NON_PASSPORT', 30, null),
    ('l-my-oku-logo2', 'Logo 2', 'NON_PASSPORT', 30, null),
    ('l-my-oku-oku', 'Oku', 'NON_PASSPORT', 30, null),
    ('l-my-oku-rukun', 'Rukun', 'NON_PASSPORT', 30, null),
    ('l-my-oku-back-oku', 'Oku', 'NON_PASSPORT', 30, null),
    ('l-mk-id-header', 'MyKid Header', 'NON_PASSPORT', 30, null),
    ('l-mk-id-logo', 'MyKid Logo', 'NON_PASSPORT', 30, null),
    ('l-mk-id-flag', 'MyKid Flag', 'NON_PASSPORT', 30, null),
    ('l-mk-id-chip', 'MyKid Chip', 'NON_PASSPORT', 30, null),
    ('l-my-poca-header', 'Header Landmark', 'NON_PASSPORT', 30, null),
    ('l-my-poca-mypoca', 'MyPoca Landmark', 'NON_PASSPORT', 30, null),
    ('l-my-poca-flag', 'Flag Landmark', 'NON_PASSPORT', 30, null),
    ('l-my-poca-chip', 'Chip Landmark', 'NON_PASSPORT', 30, null),
    ('l-my-poca-hibiscus', 'Hibiscus Landmark', 'NON_PASSPORT', 30, null),
    ('l-my-dl-address', 'Address', 'NON_PASSPORT', 30, null),
    ('l-my-dl-class', 'Class', 'NON_PASSPORT', 30, null),
    ('l-my-dl-flag', 'Flag', 'NON_PASSPORT', 30, null),
    ('l-my-dl-header', 'Header', 'NON_PASSPORT', 30, null),
    ('l-my-dl-hibiscus', 'Hibiscus', 'NON_PASSPORT', 30, null),
    ('l-my-dl-idNo', 'Id Number', 'NON_PASSPORT', 30, null),
    ('l-my-dl-logo', 'Logo', 'NON_PASSPORT', 30, null),
    ('l-my-dl-malaysia', 'Malaysia', 'NON_PASSPORT', 30, null),
    ('l-my-dl-nationality', 'Nationality', 'NON_PASSPORT', 30, null),
    ('l-my-dl-validity', 'Validity', 'NON_PASSPORT', 30, null),

    -- Myanmar
    -- DL: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/myanmar/mm-driving-license/versions
    ('l-mm-dl-logo', 'Logo', 'NON_PASSPORT', 30, null),
    ('l-mm-dl-header-burmese', 'Myanmar Driving License In Burmese', 'NON_PASSPORT', 30, null),
    ('l-mm-dl-header', 'Myanmar Driving License', 'NON_PASSPORT', 30, null),
    ('l-mm-dl-no', 'No', 'NON_PASSPORT', 30, null),
    ('l-mm-dl-name', 'Name', 'NON_PASSPORT', 30, null),
    ('l-mm-dl-nrc-no', 'NRC No', 'NON_PASSPORT', 30, null),
    ('l-mm-dl-dob', 'Date Of Birth', 'NON_PASSPORT', 30, null),
    ('l-mm-dl-blood-type', 'Blood Type', 'NON_PASSPORT', 30, null),
    ('l-mm-dl-expiry-d', 'Valid Up To', 'NON_PASSPORT', 30, null),

    -- Philippines
    -- SSS Front: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/philippines/ph-sss/versions
    -- SSS Back: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/philippines/ph-sss-back/versions
    -- UMID: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/philippines/ph-umid/versions
    -- Voter ID: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/philippines/ph-voter-id/versions
    -- Postal ID Front: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/philippines/ph-postal-id/versions
    -- Postal ID Back: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/philippines/ph-postal-id-back/versions
    -- PRC Front: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/philippines/ph-prc-professional-id-card/versions
    -- PRC Back: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/philippines/ph-prc-professional-id-card-back/versions
    -- National ID: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/philippines/ph-national-id-philsys/versions
    -- DL: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/philippines/ph-driving-license/versions
    ('l-ph-dl-flag', 'Philippines Flag', 'NON_PASSPORT', 30, null),
    ('l-ph-dl-header', 'Header', 'NON_PASSPORT', 30, null),
    ('l-ph-dl-logo', 'Logo', 'NON_PASSPORT', 30, null),
    ('l-ph-dl-bglogo', 'Background Logo', 'NON_PASSPORT', 30, null),
    ('l-ph-sss-flag', 'Philippines Flag', 'NON_PASSPORT', 30, null),
    ('l-ph-sss-header', 'Header', 'NON_PASSPORT', 30, null),
    ('l-ph-sss-barcode', 'Barcode', 'NON_PASSPORT', 30, null),
    ('l-ph-sss-back-header', 'Header', 'NON_PASSPORT', 30, null),
    ('l-ph-umid-republic-log', 'Republic Logo', 'NON_PASSPORT', 30, null),
    ('l-ph-umid-republic-header', 'Republic Header', 'NON_PASSPORT', 30, null),
    ('l-ph-umid-logo', 'UMID Logo', 'NON_PASSPORT', 30, null),
    ('l-ph-voter-republic-log', 'Republic Logo', 'NON_PASSPORT', 30, null),
    ('l-ph-voter-republic-header', 'Republic Header', 'NON_PASSPORT', 30, null),
    ('l-ph-voter-info1', 'Personal Info 1', 'NON_PASSPORT', 30, null),
    ('l-ph-voter-info2', 'Personal Info 2', 'NON_PASSPORT', 30, null),
    ('l-ph-postal-logo', 'Logo', 'NON_PASSPORT', 30, null),
    ('l-ph-postal-header', 'Header', 'NON_PASSPORT', 30, null),
    ('l-ph-postal-philpost', 'Philpost', 'NON_PASSPORT', 30, null),
    ('l-ph-postal-back-phlpost', 'Philpost', 'NON_PASSPORT', 30, null),
    ('l-ph-b1-logo', 'Logo', 'NON_PASSPORT', 30, null),
    ('l-ph-b1-republic-header', 'Republic Header', 'NON_PASSPORT', 30, null),
    ('l-ph-b1-republic-log', 'Republic Logo', 'NON_PASSPORT', 30, null),
    ('l-ph-b1-barcode', 'Barcode', 'NON_PASSPORT', 30, null),
    ('l-ph-prc-back-header', 'header', 'NON_PASSPORT', 30, null),
    ('l-ph-prc-back-logo-1', 'logo-1', 'NON_PASSPORT', 30, null),
    ('l-ph-prc-back-logo-2', 'logo-2', 'NON_PASSPORT', 30, null),
    ('l-ph-prc-back-logo-3', 'logo-3', 'NON_PASSPORT', 30, null),
    ('l-ph-ni-logo1', 'Logo 1 Landmark', 'NON_PASSPORT', 30, null),
    ('l-ph-ni-header', 'Header Landmark', 'NON_PASSPORT', 30, null),
    ('l-ph-ni-logo2', 'Logo 2 Landmark', 'NON_PASSPORT', 30, null),
    ('l-ph-ni-lastname', 'Last Name Landmark', 'NON_PASSPORT', 30, null),
    ('l-ph-ni-givenname', 'Given Name Landmark', 'NON_PASSPORT', 30, null),
    ('l-ph-ni-midname', 'Middle Name Landmark', 'NON_PASSPORT', 30, null),
    ('l-ph-ni-dob', 'DOB Landmark', 'NON_PASSPORT', 30, null),
    ('l-ph-ni-logo3', 'Logo 3 Landmark', 'NON_PASSPORT', 30, null),

    -- Singapore
    -- ID Front: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/singapore/sg-nric-pr-front/versions
    -- ID Back: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/singapore/sg-nric-pr-back/versions
    ('l-sg-lic-coatofarm', 'Coat of Arms', 'NON_PASSPORT', 30, null),
    ('l-sg-lic-lionhead', 'Lion Head Symbol', 'NON_PASSPORT', 30, null),
    ('l-sg-lic-republic', 'Republic of Singapore', 'NON_PASSPORT', 30, null),
    ('l-sg-idb-tp', 'Thumbprint', 'NON_PASSPORT', 30, null),
    ('l-sg-idb-barcode', 'Barcode', 'NON_PASSPORT', 30, null),
    ('l-sg-idb-logo', 'Logo', 'NON_PASSPORT', 30, null),

    -- Thailand
    -- ID Front: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/thailand/th-id-card/versions
    -- ID Back: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/thailand/th-id-card-back/versions
    ('l-th-id-logo', 'Logo', 'NON_PASSPORT', 30, null),
    ('l-th-id-no', 'Identification Number', 'NON_PASSPORT', 30, null),
    ('l-th-id-card', 'Thai National ID Card', 'NON_PASSPORT', 30, null),
    ('l-th-id-name', 'Name', 'NON_PASSPORT', 30, null),
    ('l-th-id-lastname', 'Last Name', 'NON_PASSPORT', 30, null),
    ('l-th-dob', 'Date Of Birth', 'NON_PASSPORT', 30, null),
    ('l-th-issue-d', 'Date Of Issue', 'NON_PASSPORT', 30, null),
    ('l-th-id-sign', 'Sign', 'NON_PASSPORT', 30, null),
    ('l-th-id-exp-d', 'Date Of Expiry', 'NON_PASSPORT', 30, null),
    ('l-th-id-back-logo', 'Hologram', 'NON_PASSPORT', 30, null),
    ('l-th-id-back-temple', 'Temple', 'NON_PASSPORT', 30, null),
    ('l-th-id-back-flag', 'Flag', 'NON_PASSPORT', 30, null),

    -- United Arab Emirates
    -- ID: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/united-arab-emirates/uae-id-card/versions
    ('l-uae', 'UAE', 'NON_PASSPORT', 30, null),
    ('l-uae-id-card', 'Identity Card', 'NON_PASSPORT', 30, null),
    ('l-uae-arabic', 'UAE In Arabic', 'NON_PASSPORT', 30, null),
    ('l-uae-id-card-arabic', 'Identity Card Arabic', 'NON_PASSPORT', 30, null),
    ('l-uae-logo', 'Logo', 'NON_PASSPORT', 30, null),
    ('l-uae-id-no', 'ID Number', 'NON_PASSPORT', 30, null),
    ('l-uae-name', 'Name', 'NON_PASSPORT', 30, null),
    ('l-uae-nationality', 'Nationality', 'NON_PASSPORT', 30, null),

    -- Vietnam
    -- ID Old: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/vietnam/vn-old-id-card/versions
    -- ID New: https://api2-ekycapis.innov8tif.com/okaydoc/okaydoc-all/supported-documents/vietnam/vn-new-id-card/versions
    ('l-vn-old-id-front-logo', 'Logo', 'NON_PASSPORT', 30, null),
    ('l-vn-old-id-front-header1', 'Header1', 'NON_PASSPORT', 30, null),
    ('l-vn-old-id-front-header2', 'Header2', 'NON_PASSPORT', 30, null),
    ('l-vn-old-id-front-no', 'No', 'NON_PASSPORT', 30, null),
    ('l-vn-old-id-front-name', 'Name', 'NON_PASSPORT', 30, null),
    ('l-vn-old-id-front-dob', 'Date Of Birth', 'NON_PASSPORT', 30, null),
    ('l-vn-old-id-front-pob', 'Place Of Birth', 'NON_PASSPORT', 30, null),
    ('l-vn-old-id-front-address', 'Address', 'NON_PASSPORT', 30, null),
    ('l-vn-old-id-front-backgroundlandmark', 'Background Landmark', 'NON_PASSPORT', 30, null),
    ('l-vn-old-id-front-wavinglines', 'Waving Lines', 'NON_PASSPORT', 30, null),
    ('l-vn-new-id-front-logo', 'Logo', 'NON_PASSPORT', 30, null),
    ('l-vn-new-id-front-header', 'Header', 'NON_PASSPORT', 30, null),
    ('l-vn-new-id-front-expiry-d', 'Expiry Date', 'NON_PASSPORT', 30, null),
    ('l-vn-new-id-front-id-no', 'ID Number', 'NON_PASSPORT', 30, null),
    ('l-vn-new-id-front-dob', 'Date Of Birth', 'NON_PASSPORT', 30, null),
    ('l-vn-new-id-front-gender', 'Gender', 'NON_PASSPORT', 30, null),
    ('l-vn-new-id-front-hometown', 'Hometown', 'NON_PASSPORT', 30, null),
    ('l-vn-new-id-front-address', 'Address', 'NON_PASSPORT', 30, null);