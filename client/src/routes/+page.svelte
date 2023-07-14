
// This is the home page for the site, which is simply used
// to route the user to the proper initial page. If this code
// find a "token" in the local storage then we are good to
// leap right into the site. Otherwise we need to ask the user
// to authenticate first, thus they get routed to the 'login'
// page.

<script>
  import { onMount } from 'svelte'
  import { goto } from '$app/navigation';
  import { token, name, fullName } from "$lib/store.js"
  import { getUser } from '$lib/common.js'

  let email = null;
  let password = null;

	onMount(async () => {
    let t = localStorage.getItem('token');
    if (t == null) {
      // We need to log in at this point
      goto('login');
    } else {
      token.set(t);
      // The user is already authenticated, go to the overview
      goto('overview');
    }
	});
</script>
