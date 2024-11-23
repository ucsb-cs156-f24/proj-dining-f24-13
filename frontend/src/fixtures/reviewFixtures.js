const reviewFixtures = {
  oneReview: {
    id: 1,
    reviewerId: 101,
    itemId: 201,
    dateServed: "2023-11-01T12:00:00",
    stars: 3,
    reviewText: "soggy",
    status: "Awaiting Moderation",
    modId: null,
    modComments: "",
    createdDate: "2023-11-01T15:00:00",
    lastEditedDate: "2023-11-01T16:00:00",
  },
  threeReviews: [
    {
      id: 1,
      reviewerId: 102,
      itemId: 202,
      dateServed: "2023-11-01T12:00:00",
      stars: 2,
      reviewText: "not spicy",
      status: "Approved",
      modId: 301,
      modComments: "",
      createdDate: "2023-11-01T15:00:00",
      lastEditedDate: "2023-11-01T16:00:00",
    },
    {
      id: 2,
      reviewerId: 103,
      itemId: 203,
      dateServed: "2023-11-02T12:00:00",
      stars: 3,
      reviewText: "Vote for me for AS President!",
      status: "Rejected",
      modId: 302,
      modComments: "Spam",
      createdDate: "2023-11-02T15:00:00",
      lastEditedDate: "2023-11-02T16:00:00",
    },
    {
      id: 3,
      reviewerId: 104,
      itemId: 204,
      dateServed: "2023-11-03T12:00:00",
      stars: 2,
      reviewText: "too spicy",
      status: "Approved",
      modId: 303,
      modComments: "",
      createdDate: "2023-11-03T15:00:00",
      lastEditedDate: "2023-11-03T16:00:00",
    },
  ],
};

export { reviewFixtures };
