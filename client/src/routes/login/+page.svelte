
<script>
  import { onMount } from 'svelte'
  import { goto } from '$app/navigation';
  import { token, name, fullName } from "$lib/store.js"
  import { getUser } from '$lib/common.js'

  let email = null;
  let password = null;

	onMount(async () => {
    let t = localStorage.getItem('token');
    if (t != null) {
      token.set(t);
      await getUser();

      // The user is already authenticated, go to the overview
      goto('overview');
    }
	});

  const login = async () => {
    var json = JSON.stringify({
      email: email,
      password: password
    });
    const response = await fetch('/api/user/login', {
      method: "post",
      withCredentials: true,
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      //make sure to serialize your JSON body
      body: json
    });
    if ( ! response.ok) {
      console.log('User not authenticated, a password problem?.');
    } else {
      const json = await response.json();
      console.log(json);
      localStorage.setItem('token', json.token);
      name.set(json.name);
      fullName.set(json.fullName);
      token.set(json.token);

      // Go to the User's overview page
      goto('overview');
    }
  }
</script>

<section class="h-screen">
  <div class="container h-full px-6 py-22">
    <div
      class="g-6 flex h-full flex-wrap items-center justify-center lg:justify-between">
      <!-- Left column container with background-->
      <div class="mb-12 md:mb-0 md:w-8/12 lg:w-6/12">
        <img
          src="/RV7-splash-small.png"
          class="w-full rounded-lg overflow-hidden"
          alt="RV7 Splash Image" />
      </div>

      <!-- Right column container with form -->
      <div class="md:w-8/12 lg:ml-6 lg:w-5/12">
        <div class="font-sans text-[30px]">
          Planez.co Student Portal
        </div>

        <!-- Divider -->
        <div
          class="my-4 flex items-center flex-1 border-t border-neutral-300">
          <p
            class="mx-4 mb-0 text-center font-semibold dark:text-neutral-200">
          </p>
        </div>

        <form>
          <!-- Email input -->
          <div class="relative mb-6" data-te-input-wrapper-init>
            <input
              bind:value={email}
              type="text"
              class="min-h-[auto] w-full border-b-4 px-3 py-[0.32rem] leading-[2.15] outline-none"
              id="requestLoginInput"
              placeholder="Email address" />
          </div>

          <!-- Password input -->
          <div class="relative mb-6" data-te-input-wrapper-init>
            <input
              bind:value={password}
              type="password"
              class="min-h-[auto] w-full border-b-4 px-3 py-[0.32rem] leading-[2.15] outline-none"
              id="exampleFormControlInput33"
              placeholder="Password" />
          </div>

          <!-- Remember me checkbox -->
          <div class="mb-6 flex items-center justify-between">
            <div class="mb-[0.125rem] block min-h-[1.5rem] pl-[1.5rem]">
              <input
                class="relative float-left -ml-[1.5rem] mr-[6px] mt-[0.15rem] h-[1.125rem] w-[1.125rem] appearance-none rounded-[0.25rem] border-[0.125rem] border-solid border-neutral-300 outline-none before:pointer-events-none before:absolute before:h-[0.875rem] before:w-[0.875rem] before:scale-0 before:rounded-full before:bg-transparent before:opacity-0 before:shadow-[0px_0px_0px_13px_transparent] before:content-[''] checked:border-primary checked:bg-primary checked:before:opacity-[0.16] checked:after:absolute checked:after:-mt-px checked:after:ml-[0.25rem] checked:after:block checked:after:h-[0.8125rem] checked:after:w-[0.375rem] checked:after:rotate-45 checked:after:border-[0.125rem] checked:after:border-l-0 checked:after:border-t-0 checked:after:border-solid checked:after:border-white checked:after:bg-transparent checked:after:content-[''] hover:cursor-pointer hover:before:opacity-[0.04] hover:before:shadow-[0px_0px_0px_13px_rgba(0,0,0,0.6)] focus:shadow-none focus:transition-[border-color_0.2s] focus:before:scale-100 focus:before:opacity-[0.12] focus:before:shadow-[0px_0px_0px_13px_rgba(0,0,0,0.6)] focus:before:transition-[box-shadow_0.2s,transform_0.2s] focus:after:absolute focus:after:z-[1] focus:after:block focus:after:h-[0.875rem] focus:after:w-[0.875rem] focus:after:rounded-[0.125rem] focus:after:content-[''] checked:focus:before:scale-100 checked:focus:before:shadow-[0px_0px_0px_13px_#3b71ca] checked:focus:before:transition-[box-shadow_0.2s,transform_0.2s] checked:focus:after:-mt-px checked:focus:after:ml-[0.25rem] checked:focus:after:h-[0.8125rem] checked:focus:after:w-[0.375rem] checked:focus:after:rotate-45 checked:focus:after:rounded-none checked:focus:after:border-[0.125rem] checked:focus:after:border-l-0 checked:focus:after:border-t-0 checked:focus:after:border-solid checked:focus:after:border-white checked:focus:after:bg-transparent dark:border-neutral-600 dark:checked:border-primary dark:checked:bg-primary dark:focus:before:shadow-[0px_0px_0px_13px_rgba(255,255,255,0.4)] dark:checked:focus:before:shadow-[0px_0px_0px_13px_#3b71ca]"
                type="checkbox"
                value=""
                id="exampleCheck3"
                checked />
              <label
                class="inline-block pl-[0.15rem] hover:cursor-pointer"
                for="exampleCheck3">
                Remember me
              </label>
            </div>

            <!-- Forgot password link -->
            <a
              href="#!"
              class="text-primary transition duration-150 ease-in-out hover:text-primary-600 focus:text-primary-600 active:text-primary-700 dark:text-primary-400 dark:hover:text-primary-500 dark:focus:text-primary-500 dark:active:text-primary-600"
              >Forgot password?</a
            >
          </div>

          <!-- Submit button -->
          <button
            on:click={login.bind()}
            type="submit"
            class="inline-block w-full rounded bg-primary px-7 pb-2.5 pt-3 text-sm font-medium uppercase leading-normal text-white shadow-[0_4px_9px_-4px_#3b71ca] transition duration-150 ease-in-out hover:bg-primary-600 hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:bg-primary-600 focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:outline-none focus:ring-0 active:bg-primary-700 active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] dark:shadow-[0_4px_9px_-4px_rgba(59,113,202,0.5)] dark:hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)]"
            data-te-ripple-init
            data-te-ripple-color="light">
            Sign in
          </button>

          <!-- Divider -->
          <div class='divider'>OR</div>

          <!-- Email input -->
          <div class="relative mb-6" data-te-input-wrapper-init>
            <input
              bind:value={email}
              type="text"
              class="min-h-[auto] w-full border-b-4 px-3 py-[0.32rem] leading-[2.15] outline-none"
              id="requestEmailInput"
              placeholder="Email address" />
          </div>

          <!-- Invitatoin Request buttons -->
          <a
            class="mb-3 flex w-full items-center justify-center rounded bg-primary px-7 pb-2.5 pt-3 text-center text-sm font-medium uppercase leading-normal text-white shadow-[0_4px_9px_-4px_#3b71ca] transition duration-150 ease-in-out hover:bg-primary-600 hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:bg-primary-600 focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:outline-none focus:ring-0 active:bg-primary-700 active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] dark:shadow-[0_4px_9px_-4px_rgba(59,113,202,0.5)] dark:hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)]"
            style="background-color: #3b5998"
            href="#!"
            role="button"
            data-te-ripple-init
            data-te-ripple-color="light">
            Request Invitation
          </a>
        </form>
      </div>
    </div>
  </div>
</section>
