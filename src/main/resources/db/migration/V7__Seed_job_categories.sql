INSERT INTO job_categories (id, parent_id, name, slug, icon_url, description, sort_order, created_at) VALUES

-- Root categories
('a1000000-0000-0000-0000-000000000001', NULL, 'Web Development', 'web-development', NULL, 'Jobs related to web and application development', 1, NOW()),
('a1000000-0000-0000-0000-000000000002', NULL, 'Design & Creative', 'design-creative', NULL, 'Jobs related to graphic design, UI/UX, and creative work', 2, NOW()),
('a1000000-0000-0000-0000-000000000003', NULL, 'Data & AI', 'data-ai', NULL, 'Jobs related to data science, machine learning, and AI', 3, NOW()),
('a1000000-0000-0000-0000-000000000004', NULL, 'Mobile Development', 'mobile-development', NULL, 'Jobs related to iOS and Android development', 4, NOW()),
('a1000000-0000-0000-0000-000000000005', NULL, 'Writing & Translation', 'writing-translation', NULL, 'Jobs related to content writing, copywriting, and translation', 5, NOW()),

-- Web Development subcategories
('a2000000-0000-0000-0000-000000000001', 'a1000000-0000-0000-0000-000000000001', 'Frontend Development', 'frontend-development', NULL, NULL, 1, NOW()),
('a2000000-0000-0000-0000-000000000002', 'a1000000-0000-0000-0000-000000000001', 'Backend Development', 'backend-development', NULL, NULL, 2, NOW()),
('a2000000-0000-0000-0000-000000000003', 'a1000000-0000-0000-0000-000000000001', 'Full Stack Development', 'full-stack-development', NULL, NULL, 3, NOW()),
('a2000000-0000-0000-0000-000000000004', 'a1000000-0000-0000-0000-000000000001', 'WordPress & CMS', 'wordpress-cms', NULL, NULL, 4, NOW()),
('a2000000-0000-0000-0000-000000000005', 'a1000000-0000-0000-0000-000000000001', 'E-commerce Development', 'ecommerce-development', NULL, NULL, 5, NOW()),

-- Design & Creative subcategories
('a2000000-0000-0000-0000-000000000006', 'a1000000-0000-0000-0000-000000000002', 'UI/UX Design', 'ui-ux-design', NULL, NULL, 1, NOW()),
('a2000000-0000-0000-0000-000000000007', 'a1000000-0000-0000-0000-000000000002', 'Logo & Brand Identity', 'logo-brand-identity', NULL, NULL, 2, NOW()),
('a2000000-0000-0000-0000-000000000008', 'a1000000-0000-0000-0000-000000000002', 'Illustration', 'illustration', NULL, NULL, 3, NOW()),
('a2000000-0000-0000-0000-000000000009', 'a1000000-0000-0000-0000-000000000002', 'Motion Graphics', 'motion-graphics', NULL, NULL, 4, NOW()),

-- Data & AI subcategories
('a2000000-0000-0000-0000-000000000010', 'a1000000-0000-0000-0000-000000000003', 'Data Analysis', 'data-analysis', NULL, NULL, 1, NOW()),
('a2000000-0000-0000-0000-000000000011', 'a1000000-0000-0000-0000-000000000003', 'Machine Learning', 'machine-learning', NULL, NULL, 2, NOW()),
('a2000000-0000-0000-0000-000000000012', 'a1000000-0000-0000-0000-000000000003', 'Data Engineering', 'data-engineering', NULL, NULL, 3, NOW()),

-- Mobile Development subcategories
('a2000000-0000-0000-0000-000000000013', 'a1000000-0000-0000-0000-000000000004', 'iOS Development', 'ios-development', NULL, NULL, 1, NOW()),
('a2000000-0000-0000-0000-000000000014', 'a1000000-0000-0000-0000-000000000004', 'Android Development', 'android-development', NULL, NULL, 2, NOW()),
('a2000000-0000-0000-0000-000000000015', 'a1000000-0000-0000-0000-000000000004', 'React Native', 'react-native', NULL, NULL, 3, NOW()),
('a2000000-0000-0000-0000-000000000016', 'a1000000-0000-0000-0000-000000000004', 'Flutter', 'flutter', NULL, NULL, 4, NOW()),

-- Writing & Translation subcategories
('a2000000-0000-0000-0000-000000000017', 'a1000000-0000-0000-0000-000000000005', 'Content Writing', 'content-writing', NULL, NULL, 1, NOW()),
('a2000000-0000-0000-0000-000000000018', 'a1000000-0000-0000-0000-000000000005', 'Copywriting', 'copywriting', NULL, NULL, 2, NOW()),
('a2000000-0000-0000-0000-000000000019', 'a1000000-0000-0000-0000-000000000005', 'Technical Writing', 'technical-writing', NULL, NULL, 3, NOW()),
('a2000000-0000-0000-0000-000000000020', 'a1000000-0000-0000-0000-000000000005', 'Translation', 'translation', NULL, NULL, 4, NOW());