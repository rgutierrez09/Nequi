USE franchise_db;

CREATE TABLE products (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          stock INT NOT NULL
);

CREATE TABLE branches (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL
);

CREATE TABLE franchises (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255) NOT NULL
);

CREATE TABLE branch_products (
                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                 branch_id INT,
                                 product_id INT,
                                 FOREIGN KEY (branch_id) REFERENCES branches(id),
                                 FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE franchise_branch (
                                  id INT AUTO_INCREMENT PRIMARY KEY,
                                  franchise_id INT,
                                  branch_id INT,
                                  FOREIGN KEY (franchise_id) REFERENCES franchises(id),
                                  FOREIGN KEY (branch_id) REFERENCES branches(id)
);
