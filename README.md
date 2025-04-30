# CampusStylist
Team Members

| Full Name              | ID           |
|------------------------|--------------|
| Bezawit Girma          | UGR/6425/15  |
| Biruck Getnet          | UGR/2525/15  |
| Leul Tesfaye           | UGR/4985/15  |
| Mekdelawit Andualem    | UGR/0663/15  |
| Netsanet Rafael        | UGR/9486/15  |


# Project Description
CampusStylist is a mobile app that connects students with hairdressers on campus, allowing students to browse available hairdressers, book appointments, and view pricing and services. Hairdressers can manage their availability, service offerings, and appointment schedules efficiently.

# Business Features (With Full CRUD Operations)
1. Hairdresser Listings Management (CRUD)
   Create – Hairdressers can create and manage their profiles, listing their services, prices, and availability.

   Read – Students can browse hairdressers, view details, search, and apply filters.

   Update – Hairdressers can edit their profile information (availability, pricing, and services).

   Delete – Hairdressers can remove their profiles if they no longer offer services.

2. Booking & Appointment Scheduling (CRUD)
   Create – Students can book appointments with hairdressers.

   Read – Both students and hairdressers can view scheduled appointments.

   Update – Students can reschedule or cancel their bookings; hairdressers can update appointment availability.

   Delete – Users can remove past or canceled appointments from their schedules.

# Roles & Permissions
Student (Users looking for hair services)
   Hairdresser Listings: Read-only (can view, search, and filter listings).

   Booking & Appointment Scheduling: Full CRUD (can book, reschedule, and cancel appointments).

Hairdresser (Users providing hair services)
   Hairdresser Listings: Full CRUD (can create, update, and delete their own profile).

   Booking & Appointment Scheduling: Read & Manage (can view and confirm/cancel appointments).

# Authentication & Authorization
Signup, Sign-in, and Delete Account

Role-Based Access Control (RBAC):

   Students and Hairdressers have different permissions for listings and bookings.

   Secure API endpoints to prevent unauthorized access.
