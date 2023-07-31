ALTER TABLE customer_roles
ADD CONSTRAINT fk_customer_roles_customer
  FOREIGN KEY (customer_id) REFERENCES customers (id) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT fk_customer_roles_role
  FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE ON UPDATE CASCADE;