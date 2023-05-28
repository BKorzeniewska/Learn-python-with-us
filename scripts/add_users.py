import argparse
import random
import requests

# List of random names and email domains
names = ["Amadeusz", "Mozart", "John", "Doe", "Alice", "Smith"]
email_domains = ["gmail.com", "yahoo.com", "hotmail.com", "example.com"]

def add_users(num_users):
    users = []

    # Generate random users
    for i in range(num_users):
        firstname = random.choice(names)
        lastname = random.choice(names)
        nickname = f"{firstname.lower()}.{lastname.lower()}"
        email = f"{nickname}{i}@{random.choice(email_domains)}"
        password = "siema"

        user = {
            "firstname": firstname,
            "lastname": lastname,
            "nickname": nickname,
            "email": email,
            "password": password
        }
        users.append(user)

    url = "http://localhost:8080/api/v1/auth/register"

    for user in users:
        response = requests.post(url, json=user)
        if response.status_code >= 200 and response.status_code < 300:
            print("User created successfully:", user["nickname"])
        else:
            print("Failed to create user:", user["nickname"])
            print("Response:", response.text)

if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("-n", type=int, help="Number of users to add")
    args = parser.parse_args()

    if args.n is not None:
        num_users = args.n
        add_users(num_users)
    else:
        print("Please provide the number of users to add using the -n argument.")
