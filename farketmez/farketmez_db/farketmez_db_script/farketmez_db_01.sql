alter table public.events
    rename column price to cost;

alter table public.events
    alter column cost type varchar(50) using cost::varchar(50);

alter table public.users
    alter column gender type varchar(50) using gender::varchar(50);