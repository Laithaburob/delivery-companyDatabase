#drop database company;

create database IF not exists Company;
use Company;
show tables ;

create table IF not exists Branch (
	Branch_ID  int UNSIGNED not null,
    Telephone_Number int UNSIGNED not null,
    Address varchar(255) not null,
    primary key (Branch_ID)
);

create table IF not exists Department (
	Department_ID  int UNSIGNED not null,
	Branch_ID  int UNSIGNED not null,
    Type_description varchar(255) not null,
    primary key (Department_ID,Branch_ID),
    foreign key (Branch_ID) references Branch(Branch_ID)
);

create table IF not exists Storages (
	Storage_ID  int UNSIGNED not null,
    Quantity_products varchar(255) not null,
    Address varchar(255) not null,
    Branch_ID int UNSIGNED not null,
    primary key (Storage_ID,Branch_ID),
    foreign key (Branch_ID) references Branch( Branch_ID)
);

create table IF not exists Employee(
	ID_Number int UNSIGNED not null ,
    Employee_Name varchar(255) not null,
    Employee_Password varchar(255) not null,
    Phone_Number int UNSIGNED not null,
    Address varchar(255) not null,
    Department_ID int UNSIGNED not null,
    Branch_ID int UNSIGNED not null,
    primary key (ID_Number,Department_ID,Branch_ID),
    foreign key (Department_ID) references Department(Department_ID),
	foreign key (Branch_ID) references Branch( Branch_ID)
);

create table IF not exists Delivery_Cars(
	Car_Number int UNSIGNED not null ,
    Car_Type varchar(255) not null,
    primary key (Car_Number)
);

create table IF not exists Customer(
	Customer_ID  int UNSIGNED not null ,
    Customer_Name varchar(255) not null,
    Phone_Number int UNSIGNED not null,
    primary key (Customer_ID)
);

create table IF not exists Product(
	Product_ID  int UNSIGNED not null ,
    Product_Name varchar(255) not null,
    primary key (Product_ID)
);

create table IF not exists Employee2Cars(
	Employee_ID int UNSIGNED not null,
	Car_Number  int UNSIGNED not null,
    Start_Date date not null,
    End_Date date,
    primary key (Employee_ID,Car_Number),
    foreign key (Employee_ID) references Employee (ID_Number),
    foreign key (Car_Number) references Delivery_Cars (Car_Number)
);

create table IF not exists Orders(
    Orders_ID int UNSIGNED not null auto_increment,
	Customer_ID int UNSIGNED not null,
	Product_ID	int UNSIGNED not null,
    Address varchar(255) not null,
    Date_of_Order DATE not null,
    Date_of_Deliver DATE ,
    primary key (Orders_ID,Customer_ID,Product_ID),
	foreign key (Customer_ID) references Customer (Customer_ID),
	foreign key (Product_ID) references Product (Product_ID)
);

#create table IF not exists Delivers_information(
#	ID int not null auto_increment,
#	Orders_ID int not null,
#    primary key (ID,Orders_ID),
#    foreign key (Orders_ID) references Orders (Orders_ID)
#);

create table IF not exists Store_in(
	Storage_ID  int UNSIGNED not null,
	Product_ID  int UNSIGNED not null,
    foreign key (Storage_ID) references Storages (Storage_ID),
    foreign key (Product_ID) references Product (Product_ID)
);
#Employee2Delivers_information
create table IF not exists Delivers_information(
	ID_Employee int UNSIGNED not null,
	Orders_ID int UNSIGNED not null,
    foreign key (ID_Employee) references Employee (ID_Number),
    foreign key (Orders_ID) references Orders (Orders_ID)
);
