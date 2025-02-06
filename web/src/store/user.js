export default {
    state: {
        id: "",
        username: "",
        email: "",
        photo: "",
        rating: "",
        participation: "",
        province: "",
        city: "",
        token: "",
        profile_username: "",
        friends: "",
    },
    getters: {

    },
    mutations: {
        updateUser(state, user) {
            state.id = user.id;
            state.username = user.username;
            state.email = user.email;
            state.photo = user.photo;
            state.rating = user.rating;
            state.participation = user.participation;
            state.province = user.province;
            state.city = user.city;
            state.friends = user.friends;
        },
        updateToken(state, token) {
            state.token = token;
        },
        updateProfileUsername(state, profile_username) {
            state.profile_username = profile_username;
        },
    },
    actions: {

    },
    modules: {

    }
}