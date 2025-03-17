import java.util.*;
public class HotelReviewSystem {



        static class Review {
            String user;
            String comment;
            int rating;

            Review(String user, String comment, int rating) {
                this.user = user;
                this.comment = comment;
                this.rating = rating;
            }

            @Override
            public String toString() {
                return String.format("User: %s, Rating: %d, Comment: %s", user, rating, comment);
            }
        }

        static class Hotel {
            String name;
            List<Review> reviews;

            Hotel(String name) {
                this.name = name;
                this.reviews = new ArrayList<>();
            }

            void addReview(String user, String comment, int rating) {
                reviews.add(new Review(user, comment, rating));
            }

            double getAverageRating() {
                if (reviews.isEmpty()) return 0.0;
                int sum = reviews.stream().mapToInt(r -> r.rating).sum();
                return (double) sum / reviews.size();
            }

            List<Review> getReviews() {
                return reviews;
            }

            @Override
            public String toString() {
                return String.format("Hotel: %s, Average Rating: %.1f", name, getAverageRating());
            }
        }

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            Map<String, Hotel> hotels = new HashMap<>();

            while (true) {
                System.out.println("\n1. Add Review\n2. View Reviews\n3. Sort Reviews by Rating\n4. Filter Reviews by Rating\n5. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter hotel name: ");
                        String hotelName = scanner.nextLine();
                        System.out.print("Enter your name: ");
                        String userName = scanner.nextLine();
                        System.out.print("Enter your rating (1-5): ");
                        int rating = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter your comment: ");
                        String comment = scanner.nextLine();

                        hotels.computeIfAbsent(hotelName, Hotel::new)
                                .addReview(userName, comment, rating);
                        System.out.println("Review added successfully!");
                        break;

                    case 2:
                        System.out.print("Enter hotel name: ");
                        String viewHotelName = scanner.nextLine();
                        Hotel hotel = hotels.get(viewHotelName);
                        if (hotel != null) {
                            System.out.println(hotel);
                            hotel.getReviews().forEach(System.out::println);
                        } else {
                            System.out.println("Hotel not found!");
                        }
                        break;

                    case 3:
                        System.out.print("Enter hotel name: ");
                        String sortHotelName = scanner.nextLine();
                        Hotel sortHotel = hotels.get(sortHotelName);
                        if (sortHotel != null) {
                            List<Review> sortedReviews = new ArrayList<>(sortHotel.getReviews());
                            sortedReviews.sort((r1, r2) -> Integer.compare(r2.rating, r1.rating)); // Descending order
                            System.out.println("Sorted Reviews:");
                            sortedReviews.forEach(System.out::println);
                        } else {
                            System.out.println("Hotel not found!");
                        }
                        break;

                    case 4:
                        System.out.print("Enter hotel name: ");
                        String filterHotelName = scanner.nextLine();
                        System.out.print("Enter minimum rating (1-5): ");
                        int minRating = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        Hotel filterHotel = hotels.get(filterHotelName);
                        if (filterHotel != null) {
                            System.out.println("Filtered Reviews:");
                            filterHotel.getReviews().stream()
                                    .filter(r -> r.rating >= minRating)
                                    .forEach(System.out::println);
                        } else {
                            System.out.println("Hotel not found!");
                        }
                        break;

                    case 5:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }

